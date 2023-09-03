package project_pet_backEnd.scheduler;

import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class IpScheduler {
    private List<ITask> taskObserver=new ArrayList<>();
    @Scheduled(cron = "0 0 0 * * *") // 每天半夜十二點執行
    public void myScheduledTask() {
        if(taskObserver.size()==0)
            return;
        taskObserver.forEach(task->task.execute());
    }
}
