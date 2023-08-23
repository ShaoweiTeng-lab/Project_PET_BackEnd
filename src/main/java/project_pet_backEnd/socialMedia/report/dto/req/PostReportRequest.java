package project_pet_backEnd.socialMedia.report.dto.req;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostReportRequest {
    @NotBlank
    String reportContent;

}
