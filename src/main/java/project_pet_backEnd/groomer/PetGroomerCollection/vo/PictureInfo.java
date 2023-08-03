package project_pet_backEnd.groomer.PetGroomerCollection.vo;

import lombok.Data;

@Data
public class PictureInfo {
    private Integer piNo;
    private Integer porId;
    private byte[] piPicture;
    private java.util.Date piDate;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}