package project_pet_backEnd.socialMedia.report.dto.req;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MessageReportRequest {
    int messageId;
    int userId;
    @Size(max = 20, message = "text size must be less than twenty")
    String mesRepContent;
}
