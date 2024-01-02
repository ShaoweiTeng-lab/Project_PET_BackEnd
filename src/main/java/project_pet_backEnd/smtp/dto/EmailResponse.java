package project_pet_backEnd.smtp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse  implements Serializable {
    private String to;      // 收件人地址
    private String subject; // 郵件主題
    private String body;    // 郵件正文
}