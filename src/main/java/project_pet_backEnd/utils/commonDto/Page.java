package project_pet_backEnd.utils.commonDto;

import lombok.Data;

/**
 * 分頁功能
 * */
@Data
public class Page<T>{
    private  Integer limit;
    private  Integer offset;

    private  Integer total;
    private  T rs;
}
