package project_pet_backEnd.socialMedia.report.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.user.vo.User;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID", insertable = false, updatable = false)
    @JsonIgnore
    private POST post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

}
