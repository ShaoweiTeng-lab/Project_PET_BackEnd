package project_pet_backEnd.manager.dto;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;
import java.util.List;

@Data
public class ManagerQueryResponse {
    private String managerAccount;
    private String managerCreated;
    private String managerState; //0關閉 ，1 啟用
}
