package project_pet_backEnd.homepageManage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dao.HomepageManageDao;
import project_pet_backEnd.homepageManage.dto.*;
import project_pet_backEnd.homepageManage.service.HomepageManageService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public class HomepageManageServiceImp implements HomepageManageService {
    @Autowired
    private HomepageManageDao homepageManageDao;

    @Override
    public ResultResponse addRotePic(AddRotePicRequest addRotePicRequest) {
        homepageManageDao.addRotePic(addRotePicRequest);
        return null;
    }

    @Override
    public ResultResponse editRotePicByPicNo(AdjustRotePicRequest adjustRotePicRequest) {
        homepageManageDao.editRotePicByPicNo(adjustRotePicRequest);
        return null;
    }

    @Override
    public void deleteRotePicByPicNo(Integer picNo) {
        homepageManageDao.deleteRotePicByPicNo(picNo);
    }

    @Override
    public List<PicRot> getAllRotePic() {
        return homepageManageDao.getAllRotePic();
    }

    @Override
    public ResultResponse addNews(AddNewsRequest addNewsRequest) {
        homepageManageDao.addNews(addNewsRequest);
        return null;
    }

    @Override
    public void editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest) {
        homepageManageDao.editNewsByNewsNo(adjustNewsRequest);
    }

    @Override
    public void deleteNewsByNewsNo(Integer newsNo) { homepageManageDao.deleteNewsByNewsNo(newsNo);

    }

    @Override
    public List<News> getAllNews() {
        return homepageManageDao.getAllNews();
    }

    @Override
    public void addNewsPic(AddNewsPicRequest addNewsPicRequest) {
        homepageManageDao.addNewsPic(addNewsPicRequest);
    }

    @Override
    public void editNewsPic(AdjustNewsPicRequest adjustNewsPicRequest) {
        homepageManageDao.editNewsPic(adjustNewsPicRequest);
    }

    @Override
    public void deleteNewsPicByNewsNo(Integer newsNo, byte[] newsPic) {
        homepageManageDao.deleteNewsPicByNewsNo(newsNo);
    }

    @Override
    public List<NewsPic> getAllNewsPic() {
        return homepageManageDao.getAllNewsPic();
    }


}
