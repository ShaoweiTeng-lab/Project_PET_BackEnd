package project_pet_backEnd.homepage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project_pet_backEnd.homepage.dao.ActivityHomeDao;
import project_pet_backEnd.homepage.dao.HomepageDao;
import project_pet_backEnd.homepage.dto.AcNewRes;
import project_pet_backEnd.homepage.service.HomepageService;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.socialMedia.activityManager.dto.ActivityRes;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomepageServiceImp implements HomepageService {
    @Value("${google-map-key:none}")
    private String googleMapKey;
    @Autowired
    private HomepageDao homepageDao;

    @Autowired
    private ActivityHomeDao activityHomeDao;

    @Override
    public String getGoogleMapApiKey() {
        return googleMapKey;
    }

    @Override
    public List<String> getRotePic() {
        List<PicRot> picRotList = homepageDao.getRotePic();
        List<String> rsList = new ArrayList<>();
        for (int i = 0; i < picRotList.size(); i++) {
            if (picRotList.get(i).getPicRotStatus() == 0)
                continue;
            byte[] pic = picRotList.get(i).getPic();
            String encodePic = AllDogCatUtils.base64Encode(pic);
            rsList.add(encodePic);
        }
        return rsList;
    }

    @Override
    public ResultResponse getNews() {
        List<News> newsList = homepageDao.getNews();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setMessage(newsList);
        return resultResponse;
    }

    @Override
    public List<NewsPic> getNewsPic() {
        return homepageDao.getNewsPic();
    }

    @Override
    public ResultResponse<List<AcNewRes>> getNewsActivities() {
        List<Activity> activityList = activityHomeDao.findAllByStatus(0, Sort.by("activityTime").descending());

        List<AcNewRes> activityResList = new ArrayList<>();
        for (Activity activity : activityList) {
            AcNewRes activityRes = new AcNewRes();
            if (activityResList.size() < 4) {
                activityRes.setActivityContent(activity.getActivityContent());
                activityRes.setActivityTitle(activity.getActivityTitle());
                activityRes.setActivityPicture(AllDogCatUtils.base64Encode(activity.getActivityPicture()));
                activityResList.add(activityRes);
            }
        }

        ResultResponse<List<AcNewRes>> response = new ResultResponse<>();
        response.setMessage(activityResList);
        return response;
    }
}
