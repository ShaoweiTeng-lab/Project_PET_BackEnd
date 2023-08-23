package project_pet_backEnd.socialMedia.util;

import lombok.Data;

import java.util.List;

@Data
public class PageRes<T> {
    List<T> ResList;
    //總共頁數
    Integer totalPage;
    //每頁資料筆數
    Integer pageSize;
    //目前所在頁數
    Integer currentPageNumber;
    //目前資料筆數
    Integer currentPageDataSize;

}
