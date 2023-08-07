package project_pet_backEnd.groomer.groomerleave.dto;

import lombok.Data;


import java.sql.Date;

@Data
public class PGLeaveSearchRes {
    private String pgName;
    private Integer leaveNo;
    private Integer pgId;
    private Date leaveCreated; //sql.date
    private Date leaveDate; //sql.date
    private String leaveTime;
    private int leaveState;
}
