package project_pet_backEnd.groomer.groomerworkmanager.vo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "PICTURE_INFO")
@Data
public class PictureInfo {
    //作品圖片
    private Integer piNo;
    private Integer porId;
    private byte[] piPicture;
    private java.util.Date piDate;//util.date

    // 此處省略建構子、Getter 和 Setter 方法
}