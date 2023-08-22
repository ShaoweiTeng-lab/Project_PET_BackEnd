package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

@Data
public class PortfolioRes {
    private Integer porId;
    private Integer pgId;
    private String porTitle;
    private String porText;
    private String porUpload;//util.date
    // 此處省略建構子、Getter 和 Setter 方法
}

