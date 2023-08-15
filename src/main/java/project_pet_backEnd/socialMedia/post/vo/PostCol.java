package project_pet_backEnd.socialMedia.post.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

}
