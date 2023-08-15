package project_pet_backEnd.groomer.appointment.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.appointment.dao.GroomerAppointmentDao;
import project_pet_backEnd.groomer.appointment.dto.AppointmentListForUser;
import project_pet_backEnd.groomer.appointment.dto.GroomerAppointmentQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.PageForAppointment;
import project_pet_backEnd.groomer.appointment.dto.UserAppoQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentCompleteOrCancelReq;
import project_pet_backEnd.groomer.appointment.dto.request.AppointmentModifyReq;
import project_pet_backEnd.groomer.appointment.dto.request.InsertAppointmentForUserReq;
import project_pet_backEnd.groomer.appointment.dto.response.*;
import project_pet_backEnd.groomer.appointment.service.GroomerAppointmentService;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;
import project_pet_backEnd.groomer.petgroomer.dao.PetGroomerDao;
import project_pet_backEnd.groomer.petgroomer.vo.PetGroomer;
import project_pet_backEnd.groomer.petgroomerschedule.dao.PetGroomerScheduleDao;
import project_pet_backEnd.groomer.appointment.utils.AppointmentUtils;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PetGroomerScheduleForAppointment;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GroomerAppointmentServiceImp implements GroomerAppointmentService {
    @Autowired
    PetGroomerDao petGroomerDao;
    @Autowired
    PetGroomerScheduleDao petGroomerScheduleDao;

    @Autowired
    GroomerAppointmentDao groomerAppointmentDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    /*
     * 進入頁面後。取得全美容師給使用者選擇。進入預約頁面的使用者預先填寫電話。
     * 姓名單純顯示。不可修改。
     */
    @Override
    public PageForAppointment<List<GetAllGroomersForAppointmentRes>> getAllGroomersForAppointment(Integer userId) {

            // 查詢數據庫
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            if (allGroomer.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到可預約之美容師");
            }

            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList = new ArrayList<>();
            for (PetGroomer petGroomer : allGroomer) {
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender = petGroomer.getPgGender();
                switch (gender) {
                    case 0:
                        res.setPgGender("女性");
                        break;
                    case 1:
                        res.setPgGender("男性");
                        break;
                }
                res.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                getAllGroomersForAppointmentResList.add(res);
            }

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment<List<GetAllGroomersForAppointmentRes>> page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            page.setUserId(userId);
            return page;
    }

    /*
     *配合getAllGroomersForAppointment 需在前端自帶隱含input 有pgId來使用
     * 取得當前伺服器(含當天)一個月內的該pg班表
     */
    @Override
    public List<PetGroomerScheduleForAppointment> getGroomerScheduleByPgId(Integer pgId) {
        // generateCurrentServerTime方法獲取現在的伺服器日期
        Date currentServerDate = AppointmentUtils.generateCurrentServerTime();

        List<PetGroomerSchedule> petGroomerSchedulesList = petGroomerScheduleDao.getAllPgScheduleRecentMonth(pgId, currentServerDate);
        if (petGroomerSchedulesList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到該美容師一個月內班表!");
        }

        // 將PetGroomerSchedule轉換為PetGroomerScheduleForAppointment
        List<PetGroomerScheduleForAppointment> appointmentScheduleList = new ArrayList<>();
        for (PetGroomerSchedule schedule : petGroomerSchedulesList) {
            PetGroomerScheduleForAppointment appointmentSchedule = new PetGroomerScheduleForAppointment();
            appointmentSchedule.setPgsId(schedule.getPgsId());
            appointmentSchedule.setPgId(schedule.getPgId());
            appointmentSchedule.setPgsDate(AllDogCatUtils.timestampToSqlDateFormat(schedule.getPgsDate()));
            appointmentSchedule.setPgsState(schedule.getPgsState());
            appointmentScheduleList.add(appointmentSchedule);
        }

        return appointmentScheduleList;
    }

    /*
     * 前台 for User 預約美容師(新增預約單)
     */
    @Override
    @Transactional
    public ResultResponse insertNewAppointmentAndUpdateSchedule(Integer userId,InsertAppointmentForUserReq insertAppointmentForUserReq) {
        PetGroomerSchedule pgSchedule;
        //修改美容師班表
        try {
            pgSchedule = petGroomerScheduleDao.getPgScheduleByPgIdAndPgsDate(insertAppointmentForUserReq.getPgId(), AllDogCatUtils.dateFormatToSqlDate(insertAppointmentForUserReq.getPgaDate()));
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
        }

        if(pgSchedule==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "預約失敗，無班表可預約!");
        }
        // 驗證只能有1個1，其餘都是0
        String pgaTime = insertAppointmentForUserReq.getPgaTime();
        Pattern pattern = Pattern.compile("^0*1{1}0*$");
        Matcher matcherFlag = pattern.matcher(pgaTime);

        if (pgaTime.length() != 24 || !matcherFlag.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "預約錯誤，請重新預約!");
        }

        // 班表狀態和預約時段對應位的Char陣列
        //VARCHAR(24) 1代表要預約時段(只會有1個1)
        char[] pgaTimeChars = insertAppointmentForUserReq.getPgaTime().toCharArray();//預約時段
        // 獲取班表狀態 VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約
        char[] pgsStateChars = pgSchedule.getPgsState().toCharArray();//班表狀態時段

        // 檢查班表狀態和預約時段的對應是否匹配
        for (int i = 0; i < pgsStateChars.length; i++) {
            if (pgaTimeChars[i] == '1' && pgsStateChars[i] == '0') {
                // 将班表狀態更新为已預約（2）
                pgsStateChars[i] = '2';
            } else if (pgaTimeChars[i] == '1' && (pgsStateChars[i] == '1' || pgsStateChars[i] == '2')) {
                // 預約時段為1，但班表狀態已經是1或2，拋出預約失敗異常
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "預約失敗，該時段不可預約!");
            }
        }

        // 更新班表狀態
        String updatedPgsState = new String(pgsStateChars);
        pgSchedule.setPgsState(updatedPgsState);

        // 更新班表
        petGroomerScheduleDao.updatePgScheduleByPgsId(pgSchedule);

        //新增預約單進SQL資料庫
        PetGroomerAppointment petGroomerAppointment = new PetGroomerAppointment();

        petGroomerAppointment.setPgId(insertAppointmentForUserReq.getPgId());
        petGroomerAppointment.setUserId(userId);
        try {
            petGroomerAppointment.setPgaDate(AllDogCatUtils.dateFormatToSqlDate(insertAppointmentForUserReq.getPgaDate()));
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "日期格式有誤!", e);
        }
        petGroomerAppointment.setPgaTime(insertAppointmentForUserReq.getPgaTime());
        petGroomerAppointment.setPgaOption(insertAppointmentForUserReq.getPgaOption());

        if (insertAppointmentForUserReq.getPgaState() == null) {
            petGroomerAppointment.setPgaState(0);
        }else{
            petGroomerAppointment.setPgaState(insertAppointmentForUserReq.getPgaState());
        }

        if (insertAppointmentForUserReq.getPgaNotes()!=null && !insertAppointmentForUserReq.getPgaNotes().isEmpty()){
            petGroomerAppointment.setPgaNotes(insertAppointmentForUserReq.getPgaNotes());
        }
        if (insertAppointmentForUserReq.getPgaPhone().length() != 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "手機格式有誤，請重新輸入!");
        }
        petGroomerAppointment.setPgaPhone(insertAppointmentForUserReq.getPgaPhone());
        groomerAppointmentDao.insertNewAppointment(petGroomerAppointment);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("預約成功!");
        return rs;
    }

    @Override
    public Page<List<AppoForUserListByUserIdRes>> getUserAppointmentByUserId(Integer userId, UserAppoQueryParameter userAppoQueryParameter) {
        List<AppointmentListForUser> appointmentForUserByUserId = groomerAppointmentDao.getAppointmentForUserByUserId(userId,userAppoQueryParameter);

        List<AppoForUserListByUserIdRes> resList = new ArrayList<>();
        for(AppointmentListForUser daoData:appointmentForUserByUserId){
            AppoForUserListByUserIdRes appoForUserListByUserIdRes = new AppoForUserListByUserIdRes();
            appoForUserListByUserIdRes.setPgaNo(daoData.getPgaNo());
            appoForUserListByUserIdRes.setPgaDate(AllDogCatUtils.timestampToSqlDateFormat(daoData.getPgaDate()));
            appoForUserListByUserIdRes.setPgaTime(AppointmentUtils.convertTimeFrompgaTimeString(daoData.getPgaTime()));//轉為時間x:00 ~ x:00

            switch (daoData.getPgaState()) {
                case 0 -> appoForUserListByUserIdRes.setPgaState("訂單未完成");
                case 1 -> appoForUserListByUserIdRes.setPgaState("訂單已完成");
                case 2 -> appoForUserListByUserIdRes.setPgaState("訂單已取消");
            }
            appoForUserListByUserIdRes.setPgaOption(AppointmentUtils.convertServiceOption(daoData.getPgaOption()));//預約選項轉換字串
            appoForUserListByUserIdRes.setPgaNotes(daoData.getPgaNotes());
            appoForUserListByUserIdRes.setPgaPhone(daoData.getPgaPhone());
            appoForUserListByUserIdRes.setUserName(daoData.getUserName());
            appoForUserListByUserIdRes.setPgName(daoData.getPgName());
            switch (daoData.getPgGender()) {
                case 0 -> appoForUserListByUserIdRes.setPgGender("女性");
                case 1 -> appoForUserListByUserIdRes.setPgGender("男性");
            }
            appoForUserListByUserIdRes.setPgPic(AllDogCatUtils.base64Encode(daoData.getPgPic()));
            resList.add(appoForUserListByUserIdRes);
        }
        Page page = new Page<>();
        page.setLimit(userAppoQueryParameter.getLimit());
        page.setOffset(userAppoQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = groomerAppointmentDao.countAppointmentByUserId(userId);
        page.setTotal(total);
        page.setRs(resList);

        return page;
    }

    /*
     * 修改預約 只有預約日期不可變更。 預約單/美容師ID不可為空。
     * 修改為當天就不可更改，包含當天以後。 可以更改日期
     */
    @Override
    @Transactional
    public ResultResponse modifyAppointmentByByPgaNo(AppointmentModifyReq appointmentModifyReq) {
        ResultResponse rs = new ResultResponse();
        String sourcePgaTimeV=null;
        PetGroomerAppointment existAppointment = groomerAppointmentDao.getAppointmentByPgaNo(appointmentModifyReq.getPgaNo());
        if(existAppointment==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到對應的預約單。");
        }
        //比對預約日期是否在伺服器時間當天之前（含當天）
        if(!AppointmentUtils.validateAppointment(existAppointment.getPgaDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "已於預約當天內或預約單已逾期。此預約不可修改!");
        }
        // 驗證預約單狀態 (0:未完成 / 1:完成訂單 / 2:取消, 預設: 0)
        if (appointmentModifyReq.getPgaState() != null) {
            if (appointmentModifyReq.getPgaState() != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此預約為完成或取消狀態，不可修改!");
            }
        }
        //驗證原先預約時段 。應該用不到。
        if (appointmentModifyReq.getSourcePgaTime() != null && !appointmentModifyReq.getSourcePgaTime().isEmpty()) {
            sourcePgaTimeV = AppointmentUtils.convertTimeStringToHourSlotString(appointmentModifyReq.getSourcePgaTime());
            if(!sourcePgaTimeV.equals(existAppointment.getPgaTime())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您提供的預約單。與原先的預約時間不相同!");
            }
        }

        //將預約方案字串轉為Int，用於修改。
        if(appointmentModifyReq.getPgaOption() != null && !appointmentModifyReq.getPgaOption().isEmpty()){
            int i = AppointmentUtils.convertServiceOptionToInt(appointmentModifyReq.getPgaOption());
            existAppointment.setPgaOption(i);
        }
        if(appointmentModifyReq.getPgaNotes() != null && !appointmentModifyReq.getPgaNotes().isEmpty()){
            existAppointment.setPgaNotes(appointmentModifyReq.getPgaNotes());
        }
        if(appointmentModifyReq.getPgaPhone() != null && !appointmentModifyReq.getPgaPhone().isEmpty()){
            existAppointment.setPgaPhone(appointmentModifyReq.getPgaPhone());
        }

        //當新的預約日期有填寫 與新的預約時間有填寫才更改:
        if (appointmentModifyReq.getPgaNewDate() != null && !appointmentModifyReq.getPgaNewDate().isEmpty() &&
                appointmentModifyReq.getPgaNewTime() != null && !appointmentModifyReq.getPgaNewTime().isEmpty()) {
            // 使用者兩邊都提供了 pgaNewDate 和 pgaNewTime

            //如下更新班表:

            //取得原先班表
            PetGroomerSchedule existPgSchedule = petGroomerScheduleDao.getPgScheduleByPgIdAndPgsDate(existAppointment.getPgId(), existAppointment.getPgaDate());
            if (existPgSchedule == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "修改預約失敗，請重新修改!");
            }
            //修改預約時段
            //xx:xx~xx:xx  預約時段 轉換回傳(24)String
            String sourcePgaTime = AppointmentUtils.convertTimeStringToHourSlotString(appointmentModifyReq.getSourcePgaTime());


            //--修改掉原先班表--
            //VARCHAR(24) 1代表要預約時段(只會有1個1)
            char[] sourcePgaTimeChars = sourcePgaTime.toCharArray();//預約時段
            // 獲取班表狀態 VARCHAR(24) 班表狀態時段 0:可預約 1:不可預約 2:已預約
            char[] pgsStateChars = existPgSchedule.getPgsState().toCharArray();//班表狀態時段

            // 檢查班表狀態和預約時段的對應是否匹配
            for (int i = 0; i < pgsStateChars.length; i++) {
                if (sourcePgaTimeChars[i] == '1' && pgsStateChars[i] == '2') {

                    pgsStateChars[i] = '0';
                } else if (sourcePgaTimeChars[i] == '1' && (pgsStateChars[i] == '1' || pgsStateChars[i] == '2')) {
                    // 預約時段為1，但班表狀態已經是1或2，拋出預約失敗異常
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "修改預約失敗，該時段不可預約!");
                }
            }
            String upDateExistPgsState = new String(pgsStateChars);
            existPgSchedule.setPgsState(upDateExistPgsState);
            //原本班表更新
            petGroomerScheduleDao.updatePgScheduleByPgsId(existPgSchedule);

            //--修改新預約班表--
            PetGroomerSchedule newSchedule=null;
            try {
                newSchedule = petGroomerScheduleDao.getPgScheduleByPgIdAndPgsDate(existAppointment.getPgId(), AllDogCatUtils.dateFormatToSqlDate(appointmentModifyReq.getPgaNewDate()));
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "提供新的預約日期格式有誤!");
            }
            if(newSchedule==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "您想修改的時段，美容師無班，預約失敗!");
            }
            String newTime = AppointmentUtils.convertTimeStringToHourSlotString(appointmentModifyReq.getPgaNewTime());

            //VARCHAR(24) 1代表要預約時段(只會有1個1)
            char[] newTimeChars = newTime.toCharArray();//預約時段
            char[] newPgsStateChars = newSchedule.getPgsState().toCharArray();//班表狀態時段

            // 檢查班表狀態和預約時段的對應是否匹配
            for (int i = 0; i < newPgsStateChars.length; i++) {
                if (newTimeChars[i] == '1' && newPgsStateChars[i] == '0') {

                    newPgsStateChars[i] = '2';
                } else if (newTimeChars[i] == '1' && (newPgsStateChars[i] == '1' || newPgsStateChars[i] == '2')) {
                    // 預約時段為1，但班表狀態已經是1或2，拋出預約失敗異常
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "更新預約失敗，該時段不可預約!");
                }
            }
            //新的班表更新
            String newPgsState = new String(newPgsStateChars);
            newSchedule.setPgsState(newPgsState);
            petGroomerScheduleDao.updatePgScheduleByPgsId(newSchedule);

            //--更新預約單--
            try {//yyyy-mm-dd 修改後的預約日期 ->sql.date
                existAppointment.setPgaDate(AllDogCatUtils.dateFormatToSqlDate(appointmentModifyReq.getPgaNewDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //xx:xx~xx:xx   修改後的預約時段 -> 24 (1)
            existAppointment.setPgaTime(AppointmentUtils.convertTimeStringToHourSlotString(appointmentModifyReq.getPgaNewTime()));
            //更新預約單
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            //推播 <修改成功> 待補...
            rs.setMessage("預約單更新成功");
            return rs;
        } else if ((appointmentModifyReq.getPgaNewDate() == null || appointmentModifyReq.getPgaNewDate().isEmpty()) &&
                (appointmentModifyReq.getPgaNewTime() == null || appointmentModifyReq.getPgaNewTime().isEmpty())) {
            // 使用者兩者都沒有提供
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            //推播 <修改成功> 待補...
            rs.setMessage("預約單更新成功");
            return rs;
        } else {
            // 使用者只提供了其中一邊（pgaNewDate 或 pgaNewTime）
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請同時提供修改 預約的新日期 和 新時間。");
        }
    }

    //取消預約單or完成訂單。for User
    @Override
    public ResultResponse AppointmentCompleteOrCancel(AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq) {
        PetGroomerAppointment existAppointment = groomerAppointmentDao.getAppointmentByPgaNo(appointmentCompleteOrCancelReq.getPgaNo());
        ResultResponse rs = new ResultResponse();
        if(existAppointment==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到對應之預約單。");
        }
        if(1==existAppointment.getPgaState()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此預約單已完成，不可修改!");
        }else if(2==existAppointment.getPgaState()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此預約單已取消，不可修改!");
        }

        if(1==appointmentCompleteOrCancelReq.getPgaState()){
            existAppointment.setPgaState(appointmentCompleteOrCancelReq.getPgaState());
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            rs.setMessage("預約單完成!");
            return rs;
        }else if(2==appointmentCompleteOrCancelReq.getPgaState()){
            existAppointment.setPgaState(appointmentCompleteOrCancelReq.getPgaState());
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            rs.setMessage("預約單成功取消!");
            return rs;
        }
        return rs;
    }

    //------美容師後台管理(預約管理)-------

    //美容師管理員查詢預清單
    public Page<List<AppoForMan>> getAllAppointmentWithSearch(GroomerAppointmentQueryParameter groomerAppointmentQueryParameter) {
        List<PGAppointmentRes> searchlist = groomerAppointmentDao.getAllAppointmentWithSearch(groomerAppointmentQueryParameter);

        List<AppoForMan> resList = new ArrayList<>();

        for(PGAppointmentRes daoData:searchlist){
            AppoForMan appoForMan = new AppoForMan();
            appoForMan.setPgaNo(daoData.getPgaNo());
            appoForMan.setPgId(daoData.getPgId());
            appoForMan.setPgaDate(AllDogCatUtils.timestampToSqlDateFormat(daoData.getPgaDate()));
            appoForMan.setPgaTime(AppointmentUtils.convertTimeFrompgaTimeString(daoData.getPgaTime()));//轉為時間x:00 ~ x:00

        switch (daoData.getPgaState()) {
            case 0 -> appoForMan.setPgaState("訂單未完成");
            case 1 -> appoForMan.setPgaState("訂單已完成");
            case 2 -> appoForMan.setPgaState("訂單已取消");
        }
            appoForMan.setPgaOption(AppointmentUtils.convertServiceOption(daoData.getPgaOption()));//預約選項轉換字串
            appoForMan.setPgaNotes(daoData.getPgaNotes());
            appoForMan.setPgaPhone(daoData.getPgaPhone());
            appoForMan.setUserName(daoData.getUserName());
            appoForMan.setPgName(daoData.getPgName());
        resList.add(appoForMan);
        }
        Page page = new Page<>();
        page.setLimit(groomerAppointmentQueryParameter.getLimit());
        page.setOffset(groomerAppointmentQueryParameter.getOffset());
        Integer total = groomerAppointmentDao.countAllAppointmentWithSearch(groomerAppointmentQueryParameter);
        page.setTotal(total);
        page.setRs(resList);
        return page;
    }

    //取消預約單or完成訂單。for Man
    @Override
    public ResultResponse AppointmentCompleteOrCancelForMan(AppointmentCompleteOrCancelReq appointmentCompleteOrCancelReq) {
        PetGroomerAppointment existAppointment = groomerAppointmentDao.getAppointmentByPgaNo(appointmentCompleteOrCancelReq.getPgaNo());
        ResultResponse rs = new ResultResponse();
        if(existAppointment==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到對應之預約單。");
        }


        if(1==appointmentCompleteOrCancelReq.getPgaState()){
            existAppointment.setPgaState(appointmentCompleteOrCancelReq.getPgaState());
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            rs.setMessage("已將預約單修改為完成狀態!");
            return rs;
        }else if(2==appointmentCompleteOrCancelReq.getPgaState()){
            existAppointment.setPgaState(appointmentCompleteOrCancelReq.getPgaState());
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            rs.setMessage("已將預約單修改為取消狀態!");
            return rs;
        }else if(0==appointmentCompleteOrCancelReq.getPgaState()){
            existAppointment.setPgaState(0);
            groomerAppointmentDao.updateAppointmentByPgaNo(existAppointment);
            rs.setMessage("已將預約單修改為尚未完成狀態!");
            return rs;
        }
        rs.setMessage("修改失敗!");
        return rs;
    }


    /*
    @Override
    public PageForAppointment<List<GetAllGroomersForAppointmentRes>> getAllGroomersForAppointment(Integer userId) {
        String redisKey = "getAllGroomersForAppointmentRes";

        List<String> getAllGroomersForAppointmentRes = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (getAllGroomersForAppointmentRes == null || getAllGroomersForAppointmentRes.isEmpty()) {
            // 查詢數據庫
            List<PetGroomer> allGroomer = petGroomerDao.getAllGroomer();
            if (allGroomer.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到可預約之美容師");
            }

            // 將結果緩存到 Redis
            List<String> jsonStrings = new ArrayList<>();
            for (PetGroomer petGroomer : allGroomer) {
                try {
                    String json = objectMapper.writeValueAsString(petGroomer);
                    jsonStrings.add(json);
                } catch (JsonProcessingException e) {
                    // 如果需要，處理例外情況
                }
            }
            redisTemplate.opsForList().leftPushAll(redisKey, jsonStrings);
            redisTemplate.expire(redisKey, 60, TimeUnit.MINUTES);

            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList = new ArrayList<>();
            for (PetGroomer petGroomer : allGroomer) {
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender = petGroomer.getPgGender();
                switch (gender) {
                    case 0:
                        res.setPgGender("女性");
                        break;
                    case 1:
                        res.setPgGender("男性");
                        break;
                }
                res.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                getAllGroomersForAppointmentResList.add(res);
            }

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment<List<GetAllGroomersForAppointmentRes>> page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            return page;
        } else {
            // Redis的結果為 JSON 字串
            List<PetGroomer> groomers = new ArrayList<>();
            for (String jsonString : getAllGroomersForAppointmentRes) {
                try {
                    PetGroomer petGroomer = objectMapper.readValue(jsonString, PetGroomer.class);
                    groomers.add(petGroomer);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }

            List<GetAllGroomersForAppointmentRes> getAllGroomersForAppointmentResList = new ArrayList<>();
            for (PetGroomer petGroomer : groomers) {
                GetAllGroomersForAppointmentRes res = new GetAllGroomersForAppointmentRes();
                res.setPgId(petGroomer.getPgId());
                res.setPgName(petGroomer.getPgName());
                int gender = petGroomer.getPgGender();
                switch (gender) {
                    case 0:
                        res.setPgGender("女性");
                        break;
                    case 1:
                        res.setPgGender("男性");
                        break;
                }
                res.setPgPic(AllDogCatUtils.base64Encode(petGroomer.getPgPic()));
                getAllGroomersForAppointmentResList.add(res);
            }

            UserPhAndNameRes userPhAndNameForAppointment = groomerAppointmentDao.getUserPhAndNameForAppointment(userId);
            PageForAppointment<List<GetAllGroomersForAppointmentRes>> page = new PageForAppointment<>();
            page.setRs(getAllGroomersForAppointmentResList);
            page.setUserName(userPhAndNameForAppointment.getUserName());
            page.setUserPh(userPhAndNameForAppointment.getUserPh());
            return page;
        }
    }
     */
    /*
    @Override
    public ResultResponse getGroomerScheduleByPgId(Integer pgId) {
        // generateCurrentServerTime方法獲取現在的伺服器日期
        Date currentServerDate = AppointmentUtils.generateCurrentServerTime();

        // 構建快取的 key 值
        String redisKey = "pgschedules_" + pgId;

        List<PetGroomerScheduleForAppointment> pgScheduleByPgIdList = new ArrayList<>();

        // 先從 Redis 中取出快取資料　
        List<String> cachedSchedule = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (cachedSchedule != null && !cachedSchedule.isEmpty()) {
            // Redis中已有快取資料，將JSON String反序列化為PetGroomerScheduleForAppointment
            for (String jsonString : cachedSchedule) {
                try {
                    PetGroomerScheduleForAppointment appointmentSchedule = objectMapper.readValue(jsonString, PetGroomerScheduleForAppointment.class);
                    pgScheduleByPgIdList.add(appointmentSchedule);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }
        } else {
            // 如果Redis中沒有快取資料，從資料庫中取得資料
            List<PetGroomerSchedule> petGroomerSchedulesList = petGroomerScheduleDao.getAllPgScheduleRecentMonth(pgId, currentServerDate);
            if (petGroomerSchedulesList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到該美容師之班表");
            }
            // 將PetGroomerSchedule轉換為PetGroomerScheduleForAppointment
            pgScheduleByPgIdList = convertToAppointmentScheduleList(petGroomerSchedulesList);

            // 將結果序列化為JSON字符串後，快取到Redis中，設定60分鐘的過期時間
            List<String> jsonStrings = new ArrayList<>();
            for (PetGroomerScheduleForAppointment schedule : pgScheduleByPgIdList) {
                try {
                    String json = objectMapper.writeValueAsString(schedule);
                    jsonStrings.add(json);
                } catch (JsonProcessingException e) {
                    // 處理例外情況
                }
            }
            redisTemplate.opsForList().leftPushAll(redisKey, jsonStrings);
            redisTemplate.expire(redisKey, 60, TimeUnit.MINUTES);
        }

        ResultResponse rs = new ResultResponse();
        rs.setMessage(pgScheduleByPgIdList);
        return rs;
    }
    */
}
