package project_pet_backEnd.socialMedia.postCollection.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_coll_info")
@IdClass(TagJoinKey.class)
public class UserTagCategory {
    @Id
    @Column(name = "PC_ID")
    Integer pcId;
    @Id
    @Column(name = "USER_ID")
    Integer userId;
    //貼文收藏標籤
    @Column(name = "PCT_ID")
    Integer pctId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PC_ID", insertable = false, updatable = false)
    @JsonIgnore
    private PostCol postCol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCT_ID", insertable = false, updatable = false)
    @JsonIgnore
    private ColCategory category;

}
