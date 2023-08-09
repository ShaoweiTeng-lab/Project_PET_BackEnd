package project_pet_backEnd.groomer.appointment.dto;

import lombok.Data;

@Data
public class PageForAppointment<T> {
    private Integer userId;
    private String userName;
    private String userPh;
    private  T rs;
}
