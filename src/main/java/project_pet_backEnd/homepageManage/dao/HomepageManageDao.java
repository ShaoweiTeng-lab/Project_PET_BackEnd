package project_pet_backEnd.homepageManage.dao;

import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dto.*;

import java.util.List;

public interface HomepageManageDao {
    //增加輪播圖
    void addRotePic(AddRotePicRequest addRotePicRequest);


    //更新輪播圖
    void editRotePicByPicNo(AdjustRotePicRequest adjustRotePicRequest);

    //刪除輪播圖
    void deleteRotePicByPicNo(Integer picNo);

    List<PicRot> getAllRotePic();

    //查詢單一輪播圖
    PicRot getOneRotePic(Integer picNo);


    //增加最新消息
    Integer addNews(AddNewsRequest addNewsRequest);

    //編輯最新消息
    void editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest);

    //刪除最新消息
    void deleteNewsByNewsNo(Integer newsNo);

    List<News> getAllNews();

    News getOneNews(Integer newsNo);


    //新增最新消息圖片
    void addNewsPic(AddNewsPicRequest addNewsPicRequest);


    //編輯最新消息圖片
    void editNewsPic(AdjustNewsPicRequest adjustNewsPicRequest);

    //刪除最新消息圖片
    void deleteNewsPicByNewsNo(Integer newsNo);

    //查詢所有最新消息圖片
    List<NewsPic> getAllNewsPic();

    //查詢單一最新消息圖片
    NewsPic getOneNewsPic(Integer newsNo);
    //因為用newsNo抓newsPic, 所以參數放newsNo
}
