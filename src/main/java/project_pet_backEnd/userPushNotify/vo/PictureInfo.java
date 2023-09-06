package project_pet_backEnd.userPushNotify.vo;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "picture_info")
public class PictureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PI_NO")
    private int piNo;

    @Column(name = "POR_ID")
    private int porId;

    @Lob
    @Column(name = "PI_PICTURE")
    private byte[] piPicture;

    @Column(name = "PI_DATE")
    private Date piDate;
}
