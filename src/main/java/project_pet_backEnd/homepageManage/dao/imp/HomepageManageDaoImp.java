package project_pet_backEnd.homepageManage.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.homepage.vo.News;
import project_pet_backEnd.homepage.vo.NewsPic;
import project_pet_backEnd.homepageManage.dto.*;
import project_pet_backEnd.homepage.vo.PicRot;
import project_pet_backEnd.homepageManage.dao.HomepageManageDao;
import project_pet_backEnd.utils.AllDogCatUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HomepageManageDaoImp implements HomepageManageDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //查詢所有輪播圖
    public List<PicRot> getAllRotePic() {
        String sql = "SELECT * FROM pic_rotate ";
        Map<String, Object> map = new HashMap<>();

        List<PicRot> picRotList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PicRot>() {
            @Override
            public PicRot mapRow(ResultSet rs, int rowNum) throws SQLException {
                PicRot picRot=new PicRot();
                picRot.setPicNo(rs.getInt("PIC_NO"));
                picRot.setPicLocateUrl(rs.getString("PIC_LOCATE_URL"));
                picRot.setPic(rs.getBytes("PIC"));
                picRot.setPicRotStatus(rs.getInt("PIC_ROT_STATUS"));
                picRot.setPicRotStart(rs.getDate("PIC_ROT_START"));
                picRot.setPicRotEnd(rs.getDate("PIC_ROT_END"));
                return picRot;

            }
        }) ;
    if(picRotList.size()>0);
        return picRotList;
    }

    //編輯輪播圖
    @Override
    public void editRotePicByPicNo(AdjustRotePicRequest adjustRotePicRequest) {
        String sql="UPDATE PIC_ROTATE\n" +
                "SET PIC_NO =  :picNo,\n" +
                "    PIC_LOCATE_URL = :picLocateUrl,\n" +
                "    PIC= :pic,\n" +
                "    PIC_ROT_STATUS= :picRotStatus,\n" +
                "    PIC_ROT_START= :picRotStart,\n" +
                "    PIC_ROT_END= :picRotEnd \n" +
                "WHERE PIC_NO = :picNo ; ";
        Map<String,Object>map =new HashMap<>();
        map.put("picNo", adjustRotePicRequest.getPicNo());
        map.put("picLocateUrl",adjustRotePicRequest.getPicRotateUrl());
        map.put("pic", AllDogCatUtils.convertMultipartFileToByteArray(adjustRotePicRequest.getPic()));
        map.put("picRotStatus",adjustRotePicRequest.getPicRotStatus());
        map.put("picRotStart",adjustRotePicRequest.getPicRotStart());
        map.put("picRotEnd",adjustRotePicRequest.getPicRotEnd());
        namedParameterJdbcTemplate.update(sql,map);
    }
    //刪除輪播圖
    public void deleteRotePicByPicNo(Integer picNo) {
        String sql="delete from PIC_ROTATE where PIC_NO = :picNo";
        Map<String ,Object> map=new HashMap<>();
        map.put("picNo", picNo);
        namedParameterJdbcTemplate.update(sql,map);
    }



    //增加輪播圖
    @Override
    public void addRotePic(AddRotePicRequest addRotePicRequest) {
        String sql = "INSERT INTO PIC_ROTATE (PIC_LOCATE_URL, PIC, PIC_ROT_STATUS, PIC_ROT_START, PIC_ROT_END) " +
                "VALUES (:picLocateUrl, :pic, :picRotStatus, :picRotStart, :picRotEnd)";

        System.out.println(addRotePicRequest.getPicLocateUrl());
        Map<String, Object> map = new HashMap<>();
        map.put("picLocateUrl", addRotePicRequest.getPicLocateUrl());
        map.put("pic", addRotePicRequest.getPic());
        map.put("picRotStatus", addRotePicRequest.getPicRotStatus());
        map.put("picRotStart", addRotePicRequest.getPicRotStart());
        map.put("picRotEnd", addRotePicRequest.getPicRotEnd());

        namedParameterJdbcTemplate.update(sql, map);
    }

    //=========================================================================

    //新增最新消息
    @Override
    public void addNews(AddNewsRequest addNewsRequest) {
        String sql = "INSERT INTO NEWS (NEWS_TITLE, NEWS_CONT ) " +
                "VALUES (:newsTitle, :newsCont )";

        Map<String, Object> map = new HashMap<>();
        map.put("newsTitle", addNewsRequest.getNewsTitle());
        map.put("newsCont", addNewsRequest.getNewsCont());
      //  map.put("updateTime", addNewsRequest.getUpdateTime());

        namedParameterJdbcTemplate.update(sql, map);

    }

    //編輯最新消息
    @Override
        public void editNewsByNewsNo(AdjustNewsRequest adjustNewsRequest) {
            String sql="UPDATE NEWS\n" +
                    "SET NEWS_TITLE = :newsTitle,\n" +
                    "    NEWS_CONT= :newsCont\n" +
                    "WHERE NEWS_NO = :newsNo ; ";
            Map<String,Object>map =new HashMap<>();
            map.put("newsTitle",adjustNewsRequest.getNewsTitle());
            map.put("newsCont",adjustNewsRequest.getNewsCont());
          //  map.put("updateTime",adjustNewsRequest.getUpdateTime());
            namedParameterJdbcTemplate.update(sql,map);
    }

    //刪除最新消息
    @Override
    public void deleteNewsByNewsNo(Integer newsNo) {
        String sql="delete from NEWS where NEWS_NO = :newsNo";
        Map<String ,Object> map=new HashMap<>();
        map.put("newsNo", newsNo);
        namedParameterJdbcTemplate.update(sql,map);
    }


    //查詢所有最新消息
    @Override
    public List<News> getAllNews() {
        String sql = "SELECT * FROM NEWS ";
        Map<String, Object> map = new HashMap<>();

        List<News> newsList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<News>() {
            @Override
            public News mapRow(ResultSet rs, int rowNum) throws SQLException {
                News news=new News();
                news.setNewsTitle(rs.getString("NEWS_TITLE"));
                news.setNewsCont(rs.getString("NEWS_CONT"));
                news.setNewsStatus(rs.getInt("NEWS_STATUS"));
                news.setUpdateTime(rs.getDate("UPDATE_TIME"));
                return news;

            }
        }) ;
        return newsList;
    }

    //查詢單一最新消息
    @Override
    public List<News> getOneNews(Integer newsNo) {
        String sql = "SELECT * FROM NEWS where NEWS_NO = :newsNo";
        Map<String, Object> map = new HashMap<>();

        List<News> news = namedParameterJdbcTemplate.query(sql, map, new RowMapper<News>() {
            @Override
            public News mapRow(ResultSet rs, int rowNum) throws SQLException {
                News news=new News();
                news.setNewsTitle(rs.getString("NEWS_TITLE"));
                news.setNewsCont(rs.getString("NEWS_CONT"));
                news.setNewsStatus(rs.getInt("NEWS_STATUS"));
                news.setUpdateTime(rs.getDate("UPDATE_TIME"));
                return news;

            }
        }) ;
        return news;
    }

    //=========================================================================

    //新增最新消息圖片
    @Override
    public void addNewsPic(AddNewsPicRequest addNewsPicRequest) {
        String sql = "INSERT INTO NEWS_PIC (NEWS_NO, PIC) " +
                "VALUES (:newsNo, :pic)";

        Map<String, Object> map = new HashMap<>();
        map.put("newsNo", addNewsPicRequest.getNewsNo());
        map.put("pic", addNewsPicRequest.getPic());

        namedParameterJdbcTemplate.update(sql, map);
    }

    //編輯最新消息圖片
    @Override
    public void editNewsPic(AdjustNewsPicRequest adjustNewsPicRequest) {
        String sql="UPDATE NEWS_PIC\n" +
                "SET NEWS_NO = :newsNo,\n" +
                "    PIC= :pic\n" +
                "WHERE NEWS_PIC_NO = :newsPicNo ; ";
        Map<String,Object>map =new HashMap<>();
        map.put("newsNo",adjustNewsPicRequest.getNewsNo());
        map.put("newsPicNo", adjustNewsPicRequest.getNewsPicNo());
        map.put("pic",adjustNewsPicRequest.getPic());
        namedParameterJdbcTemplate.update(sql,map);

    }

    //刪除最新消息圖片
    @Override
    public void deleteNewsPicByNewsNo(Integer newsPicNo) {
        String sql="delete from NEWS_PIC where NEWS_PIC_NO = :newsPicNo";
        Map<String ,Object> map=new HashMap<>();
        map.put("newsPicNo", newsPicNo);
        namedParameterJdbcTemplate.update(sql,map);

    }


    //查詢所有最新消息圖片
    @Override
    public List<NewsPic> getAllNewsPic() {

        String sql = "SELECT * FROM NEWS_PIC ";
        Map<String, Object> map = new HashMap<>();

        List<NewsPic> newsPicList = namedParameterJdbcTemplate.query(sql, map, new RowMapper<NewsPic>() {
            @Override
            public NewsPic mapRow(ResultSet rs, int rowNum) throws SQLException {
                NewsPic newsPic=new NewsPic();
                newsPic.setPic(rs.getBytes("PIC"));
                return newsPic;

            }
        }) ;
        return newsPicList;

    }


}

