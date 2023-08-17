package project_pet_backEnd.socialMedia.post.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.post.dao.ReportDao;
import project_pet_backEnd.socialMedia.post.vo.MesReport;
import project_pet_backEnd.socialMedia.post.vo.PostReport;

import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*
     * 獲取所有留言檢舉清單
     */

    @Override
    public List<MesReport> getAllMesReport() {
        String sql = "select * from MESSAGE_REPORT";
        MapSqlParameterSource params = new MapSqlParameterSource();

        List<MesReport> mesReportList =
                namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
                    MesReport mesReport = new MesReport();
                    mesReport.setMesRepId(rs.getInt("MES_REPORT_ID"));
                    mesReport.setMessageId(rs.getInt("POST_MES_ID"));
                    mesReport.setUserId(rs.getInt("USER_ID"));
                    mesReport.setMessageReportContent(rs.getString("MES_REPORT_CONTENT"));
                    mesReport.setMessageReportStatus(rs.getInt("MES_REPORT_STATUS"));
                    mesReport.setCreateTime(rs.getTimestamp("MEST_REPORT_TIME"));
                    return mesReport;
                });


        return mesReportList;

    }
    /*
     *審核留言檢舉狀態
     */

    @Override
    public MesReport reviewMesReport(MesReport messageReport) {
        String sql = "UPDATE MESSAGE_REPORT " +
                "SET MES_REPORT_STATUS = :mesRepStatus, " +
                "WHERE MES_REPORT_ID = :mesRepId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("mesRepId", messageReport.getMesRepId())
                .addValue("mesRepStatus", messageReport.getMessageReportStatus());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params);
        messageReport.setMessageId(keyHolder.getKey().intValue());
        return messageReport;
    }

    @Override
    public void notifyUserByEmail(int userId) {

    }

    /*
     *獲取所有檢舉的貼文清單 where P_REPORT_ID = :postRepId
     *   P_REPORT_ID int UN AI PK
     * POST_ID int
     * USER_ID int
     * P_REPORT_CONTENT varchar(20)
     * P_REPORT_STATUS tinyint
     * P_REPORT_TIME datetime
     */

    @Override
    public List<PostReport> getAllPostReport() {
        String sql = "select * from POST_REPORT";
        MapSqlParameterSource params = new MapSqlParameterSource();

        List<PostReport> postReportList =
                namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
                    PostReport postReport = new PostReport();
                    postReport.setPostRepId(rs.getInt("P_REPORT_ID"));
                    postReport.setPostId(rs.getInt("POST_ID"));
                    postReport.setUserId(rs.getInt("USER_ID"));
                    postReport.setPostRepContent(rs.getString("P_REPORT_CONTENT"));
                    postReport.setPostRepostStatus(rs.getInt("P_REPORT_STATUS"));
                    postReport.setCreateTime(rs.getTimestamp("P_REPORT_TIME"));
                    return postReport;
                });


        return postReportList;
    }

    /*
     *審核貼文檢舉狀態
     */
    @Override
    public PostReport reviewPostReport(PostReport postReport) {
        String sql = "UPDATE POST_REPORT " +
                "SET P_REPORT_STATUS = :postRepStatus, " +
                "WHERE P_REPORT_ID = :postRepId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postRepId", postReport.getPostRepId())
                .addValue("postRepStatus", postReport.getPostRepostStatus());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params);
        postReport.setPostRepId(keyHolder.getKey().intValue());
        return postReport;

    }

    /**
     * 建立留言檢舉
     * MES_REPORT_ID int UN AI PK
     * POST_MES_ID int UN
     * USER_ID int
     * MES_REPORT_CONTENT varchar(20)
     * MES_REPORT_STATUS tinyint
     * MEST_REPORT_TIME datetime
     */

    @Override
    public MesReport createMesReport(MesReport messageReport) {

        String sql = "INSERT INTO MESSAGE_REPORT " +
                "(POST_MES_ID, USER_ID, MES_REPORT_CONTENT, MES_REPORT_STATUS) " +
                "VALUES (:postMesId, :userId, :mesRepContent, :mesRepStatus)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postMesId", messageReport.getMessageId())
                .addValue("userId", messageReport.getUserId())
                .addValue("mesRepContent", messageReport.getMessageReportContent())
                .addValue("mesRepStatus", messageReport.getMessageReportStatus());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        messageReport.setMesRepId(keyHolder.getKey().intValue());
        return messageReport;
    }

    /**
     * 建立貼文檢舉
     * P_REPORT_ID int UN AI PK
     * POST_ID int
     * USER_ID int
     * P_REPORT_CONTENT varchar(20)
     * P_REPORT_STATUS tinyint
     * P_REPORT_TIME datetime
     */

    @Override
    public PostReport createPostReport(PostReport postReport) {

        String sql = "INSERT INTO POST_REPORT " +
                "(POST_ID, USER_ID, P_REPORT_CONTENT, P_REPORT_STATUS) " +
                "VALUES (:postId, :userId, :postRepContent, :postRepStatus)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postReport.getPostId())
                .addValue("userId", postReport.getUserId())
                .addValue("postRepContent", postReport.getPostRepContent())
                .addValue("postRepStatus", postReport.getPostRepostStatus());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        postReport.setPostRepId(keyHolder.getKey().intValue());
        return postReport;

    }
}
