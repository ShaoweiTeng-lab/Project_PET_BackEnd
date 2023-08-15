package project_pet_backEnd.socialMedia.post.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "POST_STATUS")
    Integer postStatus;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "POST_ID")
    private List<Message> message;

//    @OneToMany(cascade = {CascadeType.REMOVE})
//    @JoinColumn(name = "PC_ID")
//    private List<PostCol> postColList;

}
