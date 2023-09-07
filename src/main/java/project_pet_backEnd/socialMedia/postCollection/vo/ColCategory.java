package project_pet_backEnd.socialMedia.postCollection.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "post_collect_tag")
public class ColCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCT_ID")
    Integer pctId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "PCT_NAME")
    String categoryName;
    @Column(name = "PCT_DISC")
    String description;
    @Column(name = "PCT_CTIME")
    @CreationTimestamp
    Timestamp createTime;
    @Column(name = "PCT_UTIME")
    @UpdateTimestamp
    Timestamp updateTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCT_ID", insertable = false, updatable = false)
    @JsonIgnore
    private PostCol postCol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
