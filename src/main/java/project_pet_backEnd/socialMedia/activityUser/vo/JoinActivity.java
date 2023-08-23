package project_pet_backEnd.socialMedia.activityUser.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import project_pet_backEnd.socialMedia.activityManager.vo.Activity;
import project_pet_backEnd.user.vo.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activity_participation")
@IdClass(JoinKey.class)
public class JoinActivity {
    /**
     * ACTIVITY_ID int UN
     * USER_ID int
     * ENTER_TIME timestamp
     * ENTER_COUNT tinyint
     * ENTER_STATUS tinyint
     * 0: 參加活動
     * 1: 退出活動
     * DEFAULT: 0
     */
    @Id
    @Column(name = "ACTIVITY_ID")
    Integer activityId;
    @Id
    @Column(name = "USER_ID")
    Integer userId;
    @CreationTimestamp
    @Column(name = "ENTER_TIME")
    Timestamp enterTime;
    @Column(name = "ENTER_COUNT")
    Integer peopleCount;
    @Column(name = "ENTER_STATUS")
    Integer status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ACTIVITY_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private User user;


}
