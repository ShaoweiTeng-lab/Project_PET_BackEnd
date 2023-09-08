package project_pet_backEnd.productMall.productcollection.vo;

import lombok.Data;
import org.springframework.stereotype.Component;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@Component
public class ProductCollectPk implements Serializable {

    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "USER_ID")
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCollectPk that = (ProductCollectPk) o;
        return Objects.equals(pdNo, that.pdNo) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdNo, userId);
    }
}
