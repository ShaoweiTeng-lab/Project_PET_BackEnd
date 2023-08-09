package project_pet_backEnd.homepage.service;

import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.user.dto.ResultResponse;

import java.util.List;


public interface HomepageService {
    List<String> getRotePic();
    ResultResponse getNews();
    List<NewsPic> getNewsPic();
}
