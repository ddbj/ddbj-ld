package ddbjld.api.app.feasibility.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class QueueTestService {

    @Async
    public void heavyTask() {
        try {
            log.info("start heavy task");
            TimeUnit.SECONDS.sleep(10);
            log.info("end heavy task");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Async
    public void lightTask() {
        try {
            log.info("start light task");
            TimeUnit.SECONDS.sleep(1);
            log.info("end light task");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
