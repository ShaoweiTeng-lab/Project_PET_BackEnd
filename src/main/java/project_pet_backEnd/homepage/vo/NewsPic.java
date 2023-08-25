package project_pet_backEnd.homepage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NewsPic {
    @Id
    @Column(name = "NEWS_PIC_NO")
    private Integer newsPicNo;
    @Column(name = "NEWS_NO")
    private Integer newsNo;
//    @OneToOne
//    @JoinColumn(name = "NEWS_NO", referencedColumnName = "NEWS_NO")
//    private News news;
    @Lob
    private byte[] pic;



}
