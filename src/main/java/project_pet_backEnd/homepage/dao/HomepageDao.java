package project_pet_backEnd.homepage.dao;

import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;

import java.util.List;

public interface HomepageDao {
    List<PicRot> getRotePic();
    List<News> getNews();
    List<NewsPic> getNewsPic();
}
