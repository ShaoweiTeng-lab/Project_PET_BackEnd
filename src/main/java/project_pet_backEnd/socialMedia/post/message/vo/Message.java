package project_pet_backEnd.socialMedia.post.message.vo;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import project_pet_backEnd.socialMedia.post.post.vo.POST;
import project_pet_backEnd.socialMedia.report.vo.MesReport;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
@DynamicInsert
@DynamicUpdate
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

    // 對應到 post 的外鍵屬性值
    @ManyToOne
    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID", insertable = false, updatable = false)
    private POST post;


    @OneToMany
    @JoinColumn(name = "POST_MES_ID", referencedColumnName = "POST_MES_ID")
    private List<MesReport> mesReport;

}
