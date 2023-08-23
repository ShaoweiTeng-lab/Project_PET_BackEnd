package project_pet_backEnd.groomer.petgroomerschedule.dao;

import project_pet_backEnd.groomer.petgroomerschedule.dto.PGScheduleQueryParameter;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PgScheduleSearchList;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.sql.Date;
import java.util.List;

public interface PetGroomerScheduleDao {
    /**
     * 新增美容師班表
     * @param petGroomerSchedule 要新增的美容師班表對象
     */
    public void insertNewPgSchedule(PetGroomerSchedule petGroomerSchedule);

    /**
     * 根據班表編號更新美容師班表信息
     * @param petGroomerSchedule 要更新的美容師班表對象，其中PGS_ID指定要更新的班表編號，其他屬性指定要更新的值
     */
    public void updatePgScheduleByPgsId(PetGroomerSchedule petGroomerSchedule);

    /**
     * 獲取所有美容師班表信息
     * @return 所有美容師班表的列表
     */
    public List<PgScheduleSearchList> getAllPgScheduleByPgId();

    /**
     * 根據條件獲取美容師班表信息
     * @param pgScheduleQueryParameter 查詢條件可以包含搜索關鍵字(PG_ID PGS_DATE)、排序方式、分頁等信息
     * @return 符合條件的美容師班表的列表
     */
    public List<PgScheduleSearchList>  getAllPgScheduleWithSearch(PGScheduleQueryParameter pgScheduleQueryParameter);

    /*
     * 尚未使用
     */
    public List<PetGroomerSchedule> getPgScheduleByPgId(Integer PgId);


    /*
     * GroomerAppointmentServiceImp.getGroomerScheduleByPgId 使用。取得該美容師一個月內的班表供使用者查看
     */
    public List<PetGroomerSchedule> getAllPgScheduleRecentMonth(Integer pgId, java.sql.Date currentServerDate);


    /*
     * GroomerAppointmentServiceImp.insertNewAppointmentUpdateSchedule 使用。
     * User預約時取得該筆需要修改的一筆班表。
     */
    public  PetGroomerSchedule getPgScheduleByPgIdAndPgsDate(Integer pgId, Date pgsDate);


}
