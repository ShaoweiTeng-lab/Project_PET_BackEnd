package project_pet_backEnd.socialMedia.postMessage.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.report.vo.MesReport;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "post_message")
public class Message {

//    POST_MES_ID int UN AI PK
//    POST_ID int
//    USER_ID int
//    MES_CONTENT varchar(500)
//    MES_CTIME timestamp
//    MES_UTIME timestamp
//    MES_STATUS tinyint

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_MES_ID")
    Integer messageId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "MES_CONTENT")
    String messageContent;
    @CreationTimestamp
    @Column(name = "MES_CTIME")
    Timestamp createTime;
    @UpdateTimestamp
    @Column(name = "MES_UTIME")
    Timestamp updateTime;
    @Column(name = "MES_STATUS")
    Integer messageStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID", insertable = false, updatable = false)
    @JsonIgnore
    private POST post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


}
