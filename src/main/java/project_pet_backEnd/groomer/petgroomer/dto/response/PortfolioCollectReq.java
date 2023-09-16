package project_pet_backEnd.groomer.petgroomer.dto.response;

import lombok.Data;

@Data
public class PortfolioCollectReq {
    private Integer userId;
    private String search;
    private Integer pageSize = 10;
    private Integer currentPage=  1;
    private Integer limit = 10;
    private Integer offset = 0;
}