package project_pet_backEnd.socialMedia.report.dto.req;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ReportRequest {
    @Size(max = 20, message = "text size must be less than twenty")
    String reportContent;
}

