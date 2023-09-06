package project_pet_backEnd.homepageManage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dao.HomepageManageDao;
import project_pet_backEnd.homepageManage.dao.NewsPicRepository;
import project_pet_backEnd.homepageManage.dao.NewsRepository;
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
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return rs;
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
        ResultResponse rs =new ResultResponse();
        rs.setMessage("新增成功");
        return rs;
    }

    @Override
    public ResultResponse editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest) {
        homepageManageDao.editNewsByNewsNo(adjustNewsRequest);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return rs;
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
        ResultResponse rs =new ResultResponse();
        rs.setMessage("新增成功");
        return rs;
    }

    @Override
    public ResultResponse editNewsPicByPicNo(AdjustNewsPicRequest adjustNewsPicRequest) {
        homepageManageDao.editNewsPic(adjustNewsPicRequest);
        ResultResponse rs =new ResultResponse();
        rs.setMessage("修改成功");
        return rs;
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

        // 儲存 News 對象到資料庫
        newsRepository.save(news);

        // 創建關聯的 NewsPic 對象並設置關聯到 News 對象
        NewsPic newsPic = new NewsPic();
        newsPic.setNewsNo(news.getNewsNo());

        // 儲存 NewsPic 對象到資料庫
        newsPicRepository.save(newsPic);

        // 返回結果
        ResultResponse rs= new ResultResponse();
        rs.setMessage("新增成功");
        return rs;
    }


}
