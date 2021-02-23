package mins.study.mvc.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * sse example(Server Sent Events) - Server Push
 *
 */
@Slf4j
@RestController
public class TemperatureController {
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping(value = "/temperature-stream")
    public SseEmitter event(HttpServletRequest request) {
        SseEmitter sseEmitter = new SseEmitter();
        clients.add(sseEmitter);

        sseEmitter.onError((e) -> {
            log.error("#### error : {}", e.getMessage());
            clients.remove(sseEmitter);
        });
        sseEmitter.onCompletion(() -> {
            clients.remove(sseEmitter);
        });

        return sseEmitter;
    }

    @Async
    @EventListener
    public void handleMessage(Temperature temperature) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        clients.forEach(client -> {
            try {
                client.send(temperature, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadEmitters.add(client);
            }
        });

        clients.removeAll(deadEmitters);
    }
}
