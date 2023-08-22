package project_pet_backEnd.socialMedia.post.post.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project_pet_backEnd.socialMedia.post.message.vo.Message;

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

    @JsonIgnore
    @OneToMany(
            mappedBy = "post")
    private List<Message> message;


// orphanRemoval = true, will delete detail
// cascade = {CascadeType.REMOVE}, delete all association
//    @OneToMany(cascade = {CascadeType.REMOVE})
//    @JoinColumn(name = "PC_ID")
//    private List<PostCol> postColList;

}
