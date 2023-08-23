package project_pet_backEnd.socialMedia.report.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.socialMedia.report.dto.req.ReviewReq;
import project_pet_backEnd.socialMedia.report.dto.res.MesReportDetails;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.socialMedia.report.vo.PostReport;
import project_pet_backEnd.socialMedia.util.DateUtils;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReportImpl implements ReportDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /*
     *審核留言檢舉狀態
     */

    @Override
    public String reviewMesReport(int mesRepId, ReviewReq reviewReq) {
        String sql = "UPDATE MESSAGE_REPORT " +
                "SET MES_REPORT_STATUS = :mesRepStatus, " +
                "WHERE MES_REPORT_ID = :mesRepId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("mesRepId", mesRepId)
                .addValue("mesRepStatus", reviewReq.getStatus());
        int update = namedParameterJdbcTemplate.update(sql, params);
        if (update <= 0) {
            return "審核更新失敗";
        }


        return "審核更新成功";
    }

    /*
     *審核貼文檢舉狀態(update)
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


    /*
     * 查詢此留言是否已經被檢舉
     */

    @Override
    public MesReport checkMesExist(int messageId) {
        String sql = "SELECT * FROM MESSAGE_REPORT\n" +
                "WHERE POST_MES_ID = :messageId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("messageId", messageId);

        try {
            MesReport mesResult =
                    namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                        MesReport mesReport = new MesReport();
                        mesReport.setMesRepId(rs.getInt("MES_REPORT_ID"));
                        return mesReport;
                    });

            return mesResult;
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    public PostReport checkPostExist(int postId) {
        String sql = "SELECT * FROM POST_REPORT\n" +
                "WHERE POST_ID = :postId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("postId", postId);

        try {
            PostReport postResult =
                    namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                        PostReport postReport = new PostReport();
                        postReport.setPostRepId(rs.getInt("P_REPORT_ID"));
                        return postReport;
                    });
            return postResult;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 建立留言檢舉
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
        Timestamp currentFormatTimeStamp = DateUtils.getCurrentFormatTimeStamp();
        postReport.setCreateTime(currentFormatTimeStamp);
        return postReport;

    }

}
