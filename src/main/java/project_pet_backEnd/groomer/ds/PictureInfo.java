package project_pet_backEnd.groomer.ds;

import lombok.Data;

@Data
public class PictureInfo {
    private int piNo;
    private int porId;
    private byte[] piPicture;
    private java.util.Date piDate;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}