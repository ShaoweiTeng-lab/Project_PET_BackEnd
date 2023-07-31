package project_pet_backEnd.utils.commonDto;
/**
 * 分頁功能
 * */
public class Page<T>{
    private  Integer limit;
    private  Integer offset;

    private  Integer total;
    private  T rs;
}
