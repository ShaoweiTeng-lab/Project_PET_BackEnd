package project_pet_backEnd.groomer.appointment.dao;

import project_pet_backEnd.groomer.appointment.dto.AppointmentListForUser;
import project_pet_backEnd.groomer.appointment.dto.GroomerAppointmentQueryParameter;
import project_pet_backEnd.groomer.appointment.dto.response.PGAppointmentRes;
import project_pet_backEnd.groomer.appointment.dto.response.UserPhAndNameRes;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;

import java.util.List;

public interface GroomerAppointmentDao {

    /*
     * 新增寵物美容師預約記錄
     * @param petGroomerAppointment 要新增的寵物美容師預約對象
     */
    public void insertNewAppointment(PetGroomerAppointment petGroomerAppointment);

    /*
     * 根據預約單編號更新寵物美容師預約記錄
     * @param petGroomerAppointment 要更新的寵物美容師預約對象，其中pgaNo指定要更新的預約單編號，其他屬性指定要更新的值
     */
    public void updateAppointmentByPgaNo(PetGroomerAppointment petGroomerAppointment);

    /*
     * 獲取所有寵物美容師預約記錄
     * @return 所有寵物美容師預約記錄的列表
     */
    public List<PGAppointmentRes> getAllAppointment();

    /*
     * 根據條件獲取寵物美容師預約記錄
     * @param groomerAppointmentQueryParameter 查詢條件可以包含搜索關鍵字(
     * u.USER_NAME LIKE :search OR a.USER_ID LIKE :search OR g.PG_ID LIKE :search OR
     * g.PG_NAME LIKE :search)、
     * 排序方式(
     * PGA_NO 預約單編號,PG_ID 美容師編號,PG_NAME 美容師姓名,USER_ID 會員編號,USER_NAME 會員姓名
     * PGA_DATE 預約日期,PGA_STATE 預約單狀態 0:未完成 1:完成訂單 2:取消,PGA_OPTION 預約選項)、分頁等信息
     * @return 符合條件的寵物美容師預約記錄的列表
     */
    public List<PGAppointmentRes> getAllAppointmentWithSearch(GroomerAppointmentQueryParameter groomerAppointmentQueryParameter);

    /*
     * 根據寵物美容師編號獲取該美容師的預約記錄
     * @param pgId 寵物美容師編號
     * @return 該美容師的預約記錄列表
     */
    public List<PetGroomerAppointment> getAllAppointmentByPgId(Integer pgId);

    /*
     * GroomerAppointmentServiceImp.getAllGroomersForAppointment 使用。提供進入預約頁面的使用者預先填寫電話。
     * 姓名單純顯示。不可修改。
     */
    public UserPhAndNameRes getUserPhAndNameForAppointment(Integer userId);


    /*
     *
     */
    public List<AppointmentListForUser> getAppointmentForUserByUserId(Integer userId);


}
