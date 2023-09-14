package project_pet_backEnd.homepageManage.service;

import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dto.*;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface HomepageManageService {

    List<PicRot> getAllRotePic();

    ResultResponse editRotePicByPicNo(AdjustRotePicRequest adjustRotePicRequest);

    void deleteRotePicByPicNo(Integer picNo);

    void addRotePic(AddRotePicRequest addRotePicRequest);

    //===================================================

    ResultResponse addNews(AddNewsRequest addNewsRequest);

    ResultResponse editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest);

    void deleteNewsByNewsNo(Integer newsNo);
    ResultResponse<PicRotRes> getOneRotePic(int picNo);


    List<NewsRes> getAllNews();

    ResultResponse getOneNews(Integer newsNo);
    //===========================================

    ResultResponse addNewsPic(AddNewsPicRequest addNewsPicRequest);

    ResultResponse editNewsPicByPicNo(AdjustNewsPicRequest adjustNewsPicRequest);

    void deleteNewsPicByPicNo(Integer newsPicNo);

    List<NewsPic> getAllNewsPic();


    ResultResponse addNewsPicWithNews(AddNewsPicWithNewsRequest addNewsPicWithNewsRequest);

    ResultResponse<NewsPicRes> getOneNewsPicByNewsNo(Integer newsNo);

    // 首頁上架最新消息(sort by time and status)

    ResultResponse<List<HomepageNewsRes>> getHomePageNews();


}