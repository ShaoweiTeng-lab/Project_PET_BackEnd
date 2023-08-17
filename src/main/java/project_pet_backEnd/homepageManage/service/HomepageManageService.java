package project_pet_backEnd.homepageManage.service;

import project_pet_backEnd.homepage.vo.News;
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

        ResultResponse addNews(AddNewsRequest addNewsRequest);
        void editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest);
        void deleteNewsByNewsNo(Integer newsNo);


        List<News> getAllNews();

        void addNewsPic(AddNewsPicRequest addNewsPicRequest);
        void editNewsPic(AdjustNewsPicRequest adjustNewsPicRequest);
        void deleteNewsPicByNewsNo(Integer newsPicNo, byte[] newsPic);
        List<NewsPic> getAllNewsPic();

}
