package project_pet_backEnd.socialMedia.activityManager.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activity")
public class Activity {

    /**
     * ACTIVITY_ID int UN AI PK
     * EMP_ID int
     * ACTIVITY_TITILE varchar(20)
     * ACTIVITY_CONTENT text
     * ENROLL_START date
     * ENROLL_END date
     * ACTIVITY_TIME datetime
     * ENROLLMENT_LIMIT int
     * ENROLLMENT_COUNT int
     * ACTIVITY_STATUS tinyint
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    Integer activityId;

    @Column(name = "EMP_ID")
    Integer adminId;
    @Column(name = "ACTIVITY_TITILE")
    String activityTitle;
    @Column(name = "ACTIVITY_CONTENT")
    String activityContent;
    @Column(name = "ENROLL_START")
    Timestamp startTime;
    @Column(name = "ENROLL_END")
    Timestamp endTime;
    @Column(name = "ACTIVITY_TIME")
    Timestamp activityTime;
    @Column(name = "ENROLLMENT_LIMIT")
    Integer enrollLimit;
    @Column(name = "ENROLLMENT_COUNT")
    Integer enrollTotal;
    @Column(name = "ACTIVITY_STATUS")
    Integer status;


}
