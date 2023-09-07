package project_pet_backEnd.socialMedia.post.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project_pet_backEnd.socialMedia.postMessage.vo.Message;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "post")
public class POST {
    /**
     * 不建議在使用jpa時不設定column，因為這樣如果修改變數命名會影響整個資料庫
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "POST_CONTENT")
    String postContent;
    @CreationTimestamp
    @Column(name = "POST_CTIME")
    Timestamp createTime;
    @UpdateTimestamp
    @Column(name = "POST_MTIME")
    Timestamp updateTime;
    /**
     * 0: 上架
     * 1: 下架
     */
    @Column(name = "POST_STATUS")
    Integer postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


}
