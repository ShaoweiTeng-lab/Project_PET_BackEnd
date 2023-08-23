package project_pet_backEnd.socialMedia.activityManager.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "activity")
public class Activity {

    /**
     * ACTIVITY_ID int UN AI PK
     * EMP_ID int
     * ACTIVITY_TITLE varchar(20)
     * ACTIVITY_CONTENT text
     * ENROLL_START date
     * ENROLL_END date
     * ACTIVITY_TIME datetime
     * ENROLLMENT_LIMIT int
     * ENROLLMENT_COUNT int
     * ACTIVITY_STATUS tinyint
     * 0: 已取消
     * 1: 執行中
     * DEFAULT:1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    Integer activityId;

    @Column(name = "EMP_ID")
    Integer adminId;
    @Column(name = "ACTIVITY_TITLE")
    String activityTitle;
    @Column(name = "ACTIVITY_PIC")
    @Lob
    byte[] activityPicture;
    @Column(name = "ACTIVITY_CONTENT")
    String activityContent;
    @Column(name = "ENROLL_START")
    Date startTime;
    @Column(name = "ENROLL_END")
    Date endTime;
    @Column(name = "ACTIVITY_TIME")
    Timestamp activityTime;
    @Column(name = "ENROLLMENT_LIMIT")
    Integer enrollLimit;
    @Column(name = "ENROLLMENT_COUNT")
    Integer enrollTotal;
    @Column(name = "ACTIVITY_STATUS")
    Integer status;


}