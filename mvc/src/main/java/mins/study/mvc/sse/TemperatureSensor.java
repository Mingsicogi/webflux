package mins.study.mvc.sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureSensor {

    private final ApplicationEventPublisher publisher;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        log.info("###### Start schedule");
        executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16 + random.nextGaussian() * 10;
        log.info("### pub event ###");
        publisher.publishEvent(new Temperature(temperature));
        executor.schedule(this::probe, random.nextInt(5000), TimeUnit.MILLISECONDS);
    }
}
