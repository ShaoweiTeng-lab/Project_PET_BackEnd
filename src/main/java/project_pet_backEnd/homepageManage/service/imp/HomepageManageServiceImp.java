package project_pet_backEnd.homepageManage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dao.HomepageManageDao;
import project_pet_backEnd.homepageManage.dto.*;
import project_pet_backEnd.homepageManage.service.HomepageManageService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;
@Service
public class HomepageManageServiceImp implements HomepageManageService {
    @Autowired
    private HomepageManageDao homepageManageDao;
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsPicRepository newsPicRepository;

    @Override
    public void addRotePic(AddRotePicRequest addRotePicRequest) {
        homepageManageDao.addRotePic(addRotePicRequest);
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
    public ResultResponse editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest) {
        homepageManageDao.editNewsByNewsNo(adjustNewsRequest);
        return null;
    }

    @Override
    public void deleteNewsByNewsNo(Integer newsNo) { homepageManageDao.deleteNewsByNewsNo(newsNo);

    }

    @Override
    public List<News> getAllNews() {
        return homepageManageDao.getAllNews();
    }

    @Override
    public ResultResponse addNewsPic(AddNewsPicRequest addNewsPicRequest) {
        homepageManageDao.addNewsPic(addNewsPicRequest);


        return null;
    }

    @Override
    public ResultResponse editNewsPicByPicNo(AdjustNewsPicRequest adjustNewsPicRequest) {
        homepageManageDao.editNewsPic(adjustNewsPicRequest);
        return null;
    }

    @Override
    public void deleteNewsPicByPicNo(Integer newsNo) {
        homepageManageDao.deleteNewsPicByNewsNo(newsNo);
    }

    @Override
    public List<NewsPic> getAllNewsPic() {
        return homepageManageDao.getAllNewsPic();
    }

    @Override
    public ResultResponse addNewsPicWithNews(AddNewsPicWithNewsRequest addNewsPicWithNewsRequest) {
        News news = new News();
        news.setNewsNo(addNewsPicWithNewsRequest.getNewsNo());
        news.setPic(addNewsPicWithNewsRequest.getPic());

        newsRepository.save(news);

        NewsPic newsPic = new NewsPic();
        //設置關聯的News對象
        newsPic.setNewsNo(newsNo);

        newsPicRepository.save(newsPic);

        // 返回结果
        return new ResultResponse();
    }


}
