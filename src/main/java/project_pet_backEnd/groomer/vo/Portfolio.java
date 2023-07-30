package project_pet_backEnd.groomer.vo;

import lombok.Data;

@Data
public class Portfolio {
    private int porId;
    private int pgId;
    private String porTitle;
    private String porText;
    private java.util.Date porUpload;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}
