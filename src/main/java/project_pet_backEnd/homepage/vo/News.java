package project_pet_backEnd.homepage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
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
    @UpdateTimestamp
    private Date updateTime;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Taipei")

}
