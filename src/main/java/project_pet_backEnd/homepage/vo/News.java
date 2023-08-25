package project_pet_backEnd.homepage.vo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
@Entity(name = "News")
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_No")
    private Integer newsNo;
    @Column(name = "news_title")
    private String newsTitle;
    @Column(name = "news_cont")
    private String newsCont;
    @Column(name = "news_Status")
    private Integer newsStatus;
    @Column(name = "update_Time")
    private Date updateTime;

}
