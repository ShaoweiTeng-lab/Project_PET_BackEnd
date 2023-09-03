package project_pet_backEnd.homepage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project_pet_backEnd.homepage.dao.HomepageDao;
import project_pet_backEnd.homepage.service.HomepageService;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.utils.commonDto.ResultResponse;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class HomepageServiceImp  implements HomepageService {
    @Value("${google-map-key:none}")
    private  String  googleMapKey;
    @Autowired
    private HomepageDao homepageDao;

    @Override
    public String getGoogleMapApiKey() {
        return googleMapKey;
    }

    @Override
    public List<String> getRotePic() {
        List<PicRot>  picRotList=homepageDao.getRotePic();
        List<String> rsList =new ArrayList<>();
        for(int i =0;i<picRotList.size();i++){
            if(picRotList.get(i).getPicRotStatus()==0)
                continue;
            byte[] pic=picRotList.get(i).getPic();
            String encodePic=AllDogCatUtils.base64Encode(pic);
            rsList.add(encodePic);
        }
        return rsList;
    }

    @Override
    public  ResultResponse getNews(){
        List<News> newsList = homepageDao.getNews();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setMessage(newsList);
        return resultResponse;
    }

    @Override
    public List<NewsPic> getNewsPic(){
        return homepageDao.getNewsPic();
    }
}
