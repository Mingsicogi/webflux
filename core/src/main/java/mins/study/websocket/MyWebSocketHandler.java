package mins.study.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {



        return session.receive()
                .doOnNext(webSocketMessage -> {
                  log.info("{} : {}", session.getId(), webSocketMessage.getPayloadAsText());
                })
                .then();
    }

}
