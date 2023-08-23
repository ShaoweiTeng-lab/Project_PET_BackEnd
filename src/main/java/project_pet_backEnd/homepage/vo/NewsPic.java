package project_pet_backEnd.homepage.vo;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class NewsPic {
    private Integer newsPicNo;
    private Integer newsNo;
    @Lob
    private byte[] pic;

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

}
