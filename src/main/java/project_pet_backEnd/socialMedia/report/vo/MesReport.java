package project_pet_backEnd.socialMedia.report.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "message_report")
public class MesReport {

    /**
     * db schema
     * MES_REPORT_ID int UN AI PK
     * POST_MES_ID int UN
     * USER_ID int
     * MES_REPORT_CONTENT varchar(20)
     * MES_REPORT_STATUS tinyint
     * MES_REPORT_TIME
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MES_REPORT_ID")
    Integer mesRepId;
    @Column(name = "POST_MES_ID")
    Integer messageId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "MES_REPORT_CONTENT")
    String messageReportContent;
    @CreationTimestamp
    @Column(name = "MES_REPORT_TIME")
    Timestamp messageCreateTime;
    @Column(name = "MES_REPORT_STATUS")
    Integer messageReportStatus;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POST_MES_ID", referencedColumnName = "POST_MES_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
