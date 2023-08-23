package project_pet_backEnd.groomer.petgroomercollection.vo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "PORTFOLIO_COLLECT")
@Data
public class PortfolioCollect {
    // 美容師作品收藏清單
    private Integer pcNo;
    private Integer userId;
    private Integer porId;
    private java.sql.Date pcCreated;

    // 此處省略建構子、Getter 和 Setter 方法
}
