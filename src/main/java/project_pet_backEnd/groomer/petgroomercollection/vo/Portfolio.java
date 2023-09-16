package project_pet_backEnd.groomer.petgroomercollection.vo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PORTFOLIO")
@Data
public class Portfolio {
    // 作品
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POR_ID")
    private Integer porId;
    @Column(name = "PG_ID")
    private Integer pgId;
    @Column(name = "POR_TITLE")
    private String porTitle;
    @Column(name = "POR_TEXT")
    private String porText;
    @Column(name = "POR_UPLOAD")
    private java.util.Date porUpload;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}
