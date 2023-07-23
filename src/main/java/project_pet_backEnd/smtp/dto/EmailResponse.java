package project_pet_backEnd.smtp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailResponse {
    private String to;      // 收件人地址
    private String subject; // 郵件主題
    private String body;    // 郵件正文
    public String getTo() {
        return to;
    }
    public String getSubject() {
        return subject;
    }
    public String getBody() {
        return body;
    } 
}