package project_pet_backEnd.socialMedia.activityUser.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activity_participation")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    Integer activityId;
    @Column(name = "USER_ID")
    Integer userId;
    @Column(name = "ENTER_TIME")
    Timestamp enterTime;
    @Column(name = "ENTER_COUNT")
    Integer peopleCount;
    @Column(name = "ENTER_STATUS")
    Integer status;

}
