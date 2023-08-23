package project_pet_backEnd.productMall.order.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@Component
public class OrderDetailPk implements Serializable {
    @Column(name = "ORD_NO")
    private Integer ordNo; //訂單編號 {pk,fk}
    @Column(name = "PD_NO")
    private Integer pdNo;  //商品編號 {pk,fk}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailPk that)) return false;
        return Objects.equals(getOrdNo(), that.getOrdNo()) && Objects.equals(getPdNo(), that.getPdNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrdNo(), getPdNo());
    }
}
