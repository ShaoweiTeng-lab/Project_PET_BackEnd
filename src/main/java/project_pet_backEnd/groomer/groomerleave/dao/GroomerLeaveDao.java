package project_pet_backEnd.groomer.groomerleave.dao;

import project_pet_backEnd.groomer.groomerleave.dto.GroomerLeaveQueryParameter;
import project_pet_backEnd.groomer.groomerleave.dto.response.PGLeaveSearchRes;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;

import java.util.List;

public interface GroomerLeaveDao {

    /**
     * 新增美容師請假記錄
     * @param groomerLeave 要新增的美容師請假對象
     */
    public void insertNewGroomerLeave(GroomerLeave groomerLeave);

    /**
     * 根據請假單編號更新美容師請假記錄
     * @param groomerLeave 要更新的美容師請假對象，其中leaveNo指定要更新的請假單編號，其他屬性指定要更新的值
     */
    public void updateGroomerLeaveByLeaveNo(GroomerLeave groomerLeave);

    /**
     * 獲取所有美容師請假記錄
     * @return 所有美容師請假記錄的列表
     */
    public List<PGLeaveSearchRes> getAllGroomerLeave();

    /**
     * 根據條件獲取美容師請假記錄
     * @param groomerLeaveQueryParameter 查詢條件可以包含搜索關鍵字(PG_NAME PG_ID LEAVE_DATE LEAVE_CREATED )、排序方式、分頁等信息
     * @return 符合條件的美容師請假記錄的列表
     */
    public List<PGLeaveSearchRes> getAllGroomerLeaveWithSearch(GroomerLeaveQueryParameter groomerLeaveQueryParameter);

    /**
     * 根據美容師編號獲取該美容師的請假記錄
     * @param PgId 美容師編號
     * @return 該美容師的請假記錄列表
     */
    public List<GroomerLeave> getGroomerLeaveByPgId(Integer PgId);
}
