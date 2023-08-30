package project_pet_backEnd.homepage.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
@Entity(name = "NEWS")
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NEWS_NO")
    private Integer newsNo;
    @Column(name = "NEWS_TITLE")
    private String newsTitle;
    @Column(name = "NEWS_CONT")
    private String newsCont;
    @Column(name = "NEWS_STATUS")
    private Integer newsStatus;
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

}
