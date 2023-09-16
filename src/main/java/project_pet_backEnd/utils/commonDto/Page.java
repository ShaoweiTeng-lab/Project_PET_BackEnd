package project_pet_backEnd.utils.commonDto;

import lombok.Data;

/**
 * 分頁功能
 * */
@Data
public class Page<T>{
    private  Integer limit;//限制欄位
    private  Integer offset;//從第幾筆開始

    private  Integer total;//總查詢數，可用於頁數顯示 total/limit
    private  T rs;
    private Integer page;
}
