package project_pet_backEnd.socialMedia.post.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "post_message")
public class Message {

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





}
