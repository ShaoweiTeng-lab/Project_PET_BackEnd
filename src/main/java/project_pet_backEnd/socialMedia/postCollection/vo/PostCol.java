package project_pet_backEnd.socialMedia.postCollection.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "post_collect")
public class PostCol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PC_ID")
    Integer pcId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "POST_ID")
    Integer postId;
    @CreationTimestamp
    @Column(name = "PC_CTIME")
    Timestamp createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", insertable = false, updatable = false)
    @JsonIgnore
    private POST post;


}
