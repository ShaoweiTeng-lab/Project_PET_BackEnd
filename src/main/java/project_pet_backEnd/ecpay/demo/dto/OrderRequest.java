package project_pet_backEnd.ecpay.demo.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private  String productName;
    private  Integer price;
}
