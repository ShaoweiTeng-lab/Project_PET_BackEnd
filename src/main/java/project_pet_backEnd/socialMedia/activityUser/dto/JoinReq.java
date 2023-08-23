package project_pet_backEnd.socialMedia.activityUser.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class JoinReq {
    @Min(value = 1, message = "Value should be greater then then equal to 1")
    int count;
}