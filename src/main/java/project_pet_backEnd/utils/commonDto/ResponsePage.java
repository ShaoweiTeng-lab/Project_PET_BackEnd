package project_pet_backEnd.utils.commonDto;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
public class ResponsePage<T> {
    private  Integer  page;//當前頁數
    private  Integer size;//顯示筆數
    private  Integer  total;//總筆數
    private  T body;
}
