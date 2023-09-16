package project_pet_backEnd.homepageManage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dao.HomepageManageDao;
import project_pet_backEnd.homepageManage.dao.NewsPicRepository;
import project_pet_backEnd.homepageManage.dao.NewsRepository;
import project_pet_backEnd.homepageManage.dto.*;
import project_pet_backEnd.homepageManage.service.HomepageManageService;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

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
        ResultResponse rs = new ResultResponse();
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
    public ResultResponse<PicRotRes> getOneRotePic(int picNo) {
        ResultResponse<PicRotRes> response = new ResultResponse<>();
        PicRot picRot = homepageManageDao.getOneRotePic(picNo);
        //判斷是否有找資料
        PicRotRes picRotRes = new PicRotRes();
        if (picRot.getPicNo() == null) {
            picRotRes = new PicRotRes();
            picRotRes.setPicNo(0);
            picRotRes.setPic("");
            response.setMessage(picRotRes);
            return response;
        } else {
            picRotRes.setPicNo(picRot.getPicNo());
            picRotRes.setPic(AllDogCatUtils.base64Encode(picRot.getPic()));
            picRotRes.setPicRotStatus(picRot.getPicRotStatus());
            picRotRes.setPicRotStart(DateUtils.formatStandardDate(picRot.getPicRotStart()));
            picRotRes.setPicRotEnd(DateUtils.formatStandardDate(picRot.getPicRotEnd()));
            picRotRes.setPicLocateUrl(picRot.getPicLocateUrl());
            response.setMessage(picRotRes);
        }
        return response;
    }

    @Override
    public ResultResponse addNews(AddNewsRequest addNewsRequest) {
        Integer id = homepageManageDao.addNews(addNewsRequest);
        ResultResponse rs = new ResultResponse();
        rs.setMessage(id);
        return rs;
    }
    @Transactional
    @Override
    public ResultResponse editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest) {
        homepageManageDao.editNewsByNewsNo(adjustNewsRequest);
        NewsPic newsPic= homepageManageDao.getOneNewsPic(adjustNewsRequest.getNewsNo());
        if(newsPic.getNewsNo()==null){
            AddNewsPicRequest addNewsPicRequest =new AddNewsPicRequest();
            addNewsPicRequest.setNewsNo(adjustNewsRequest.getNewsNo());
            addNewsPicRequest.setPic(AllDogCatUtils.base64Decode(adjustNewsRequest.getNewsPic()));
            homepageManageDao.addNewsPic(addNewsPicRequest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("修改成功");
            return rs;
        }
        AdjustNewsPicRequest adjustNewsPicRequest =new AdjustNewsPicRequest();
        adjustNewsPicRequest.setNewsPicNo(newsPic.getNewsPicNo());
        adjustNewsPicRequest.setNewsNo(adjustNewsRequest.getNewsNo());
        adjustNewsPicRequest.setPic(AllDogCatUtils.base64Decode(adjustNewsRequest.getNewsPic()));

        homepageManageDao.editNewsPic(adjustNewsPicRequest);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("修改成功");
        return rs;
    }

    @Override
    public void deleteNewsByNewsNo(Integer newsNo) {
        homepageManageDao.deleteNewsByNewsNo(newsNo);

    }


    @Override
    public List<NewsRes> getAllNews() {
        List<News> news = homepageManageDao.getAllNews();
        List<NewsRes> newsResList = new ArrayList<>();
        for (News oneNew : news) {
            NewsRes newsResOne = new NewsRes();
            newsResOne.setNewsCont(oneNew.getNewsCont());
            newsResOne.setNewsStatus(oneNew.getNewsStatus());
            newsResOne.setNewsTitle(oneNew.getNewsTitle());
            newsResOne.setUpdateTime(DateUtils.dateTimeSqlToStr(new Timestamp(oneNew.getUpdateTime().getTime())));
            newsResOne.setNewsNo(oneNew.getNewsNo());
            newsResList.add(newsResOne);
        }
        return newsResList;
    }

    @Override
    public ResultResponse getOneNews(Integer newsNo) {
        ResultResponse rs = new ResultResponse();
        News News;
        try {
            News = homepageManageDao.getOneNews(newsNo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "沒有此消息");
        }
        rs.setMessage(News);
        return rs;
    }

    @Override
    public ResultResponse addNewsPic(AddNewsPicRequest addNewsPicRequest) {
        homepageManageDao.addNewsPic(addNewsPicRequest);
        ResultResponse rs = new ResultResponse();
        rs.setMessage("新增成功");
        return rs;
    }

    @Override
    public ResultResponse editNewsPicByPicNo(AdjustNewsPicRequest adjustNewsPicRequest) {
        homepageManageDao.editNewsPic(adjustNewsPicRequest);
        ResultResponse rs = new ResultResponse();
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
        ResultResponse rs = new ResultResponse();
        rs.setMessage("新增成功");
        return rs;
    }

    @Override
    public ResultResponse<NewsPicRes> getOneNewsPicByNewsNo(Integer newsNo) {
        ResultResponse<NewsPicRes> response = new ResultResponse<>();
        NewsPic newsPic = homepageManageDao.getOneNewsPic(newsNo);
        //判斷是否有找資料
        NewsPicRes newsPicRes = new NewsPicRes();
        if (newsPic.getNewsNo() == null) {
            newsPicRes = new NewsPicRes();
            newsPicRes.setNewsPicNo(0);
            newsPicRes.setNewsNo(0);
            newsPicRes.setNewsPic("");
            response.setMessage(newsPicRes);
            return response;
        } else {
            newsPicRes.setNewsPicNo(newsPic.getNewsPicNo());
            newsPicRes.setNewsNo(newsPic.getNewsNo());
            newsPicRes.setNewsPic(AllDogCatUtils.base64Encode(newsPic.getPic()));
            response.setMessage(newsPicRes);
        }
        return response;
    }

    @Override
    public ResultResponse<List<HomepageNewsRes>> getHomePageNews() {
        List<News> allNewsList = newsRepository.findAllByNewsStatus(1, Sort.by("updateTime").descending());
        List<News> newsList = new ArrayList<>();
        for (News news : allNewsList) {
            if (newsList.size() < 4) {
                newsList.add(news);
            }
        }

        List<HomepageNewsRes> newsResList = new ArrayList<>();
        for (News news : newsList) {
            HomepageNewsRes homepageNewsRes = new HomepageNewsRes();
            homepageNewsRes.setNewsNo(news.getNewsNo());
            homepageNewsRes.setNewsTitle(news.getNewsTitle());
            NewsPic newsPic = newsPicRepository.findByNewsNo(news.getNewsNo());
            if(newsPic==null)
                continue;
            homepageNewsRes.setPic(AllDogCatUtils.base64Encode(newsPic.getPic()));
            newsResList.add(homepageNewsRes);
        }

        ResultResponse<List<HomepageNewsRes>> response = new ResultResponse<>();
        response.setMessage(newsResList);
        return response;
    }


}
