package project_pet_backEnd.groomer.petgroomerschedule.dto;

import lombok.Data;
import project_pet_backEnd.groomer.petgroomer.dto.orderby.PGOrderBy;
import project_pet_backEnd.userManager.dto.Sort;

@Data
public class PGScheduleQueryParameter {
    private  String search;
    private ScheduleOrderBy order;
    //PGS_ID美容師班表編號,PG_ID美容師編號,PGS_DATE班表日期
    private Sort sort;
    private  Integer limit;
    private  Integer offset;
}
