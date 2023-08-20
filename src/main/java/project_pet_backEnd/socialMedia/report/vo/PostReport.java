package project_pet_backEnd.socialMedia.report.vo;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "post_report")
public class PostReport {
    /**
     * db schema
     * P_REPORT_ID int UN AI PK
     * POST_ID int
     * USER_ID int
     * P_REPORT_CONTENT varchar(20)
     * P_REPORT_STATUS tinyint
     * P_REPORT_TIME datetime
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_REPORT_ID")
    Integer postRepId;
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "P_REPORT_CONTENT")
    String postRepContent;
    @Column(name = "P_REPORT_STATUS")
    Integer postRepostStatus;
    @CreationTimestamp
    @Column(name = "P_REPORT_TIME")
    Timestamp createTime;
    
}
