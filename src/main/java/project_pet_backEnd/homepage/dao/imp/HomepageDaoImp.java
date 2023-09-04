package project_pet_backEnd.homepage.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.homepage.dao.HomepageDao;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepage.vo.PicRot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HomepageDaoImp implements HomepageDao {
@Autowired
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<PicRot> getRotePic() {
        String sql ="select * from pic_rotate";
        Map<String ,Object> map=new HashMap<>();
        List<PicRot> picRotList=  namedParameterJdbcTemplate.query(sql, map, new RowMapper<PicRot>() {
            @Override
            public PicRot mapRow(ResultSet rs, int rowNum) throws SQLException {
                PicRot picRot=new  PicRot();
                picRot.setPicNo(rs.getInt("PIC_NO"));
                picRot.setPicLocateUrl(rs.getString("PIC_LOCATE_URL"));
                picRot.setPic(rs.getBytes("PIC"));
                picRot.setPicRotStatus(rs.getInt("PIC_ROT_STATUS"));
                picRot.setPicRotStart(rs.getDate("PIC_ROT_START"));
                picRot.setPicRotEnd(rs.getDate("PIC_ROT_END"));
                return picRot;
            }
        });
        return picRotList;
    }
    @Override
    public List<News> getNews(){
        String sql = "select * from news";
        Map<String, Object> map = new HashMap<>();
        List<News> newsList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<News>(){
            @Override
            public News mapRow(ResultSet rs, int rowNum) throws SQLException{
                News news = new News();
                news.setNewsNo(rs.getInt("NEWS_NO"));
                news.setNewsTitle(rs.getString("NEWS_TITLE"));
                news.setNewsCont(rs.getString("NEWS_CONT"));
                news.setNewsStatus(rs.getInt("NEWS_STATUS"));
                news.setUpdateTime(rs.getDate("UPDATE_TIME"));
                return news;
            }

        });
        return newsList;
    }

    @Override
    public List<NewsPic> getNewsPic(){
        String sql = "select * from news_Pic";
        Map<String, Object> map = new HashMap<>();
        List<NewsPic> newsPicList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<NewsPic>() {
            @Override
            public NewsPic mapRow(ResultSet rs, int rowNum) throws SQLException {
                NewsPic newsPic = new NewsPic();
                newsPic.setNewsPicNo(rs.getInt("NEWS_PIC_NO"));
                newsPic.setNewsNo(rs.getInt("NEWS_NO"));
                newsPic.setPic(rs.getBytes("PIC"));
                return newsPic;
            }
        });
        return newsPicList;
    }
}
