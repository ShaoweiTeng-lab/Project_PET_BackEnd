package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

@Data
public class PictureInfoRes {
    private Integer piNo;
    private Integer porId;
    private String piPicture;
    private String piDate;//util.date
    // 此處省略建構子、Getter 和 Setter 方法
}