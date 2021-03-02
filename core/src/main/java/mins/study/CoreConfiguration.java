package mins.study;

import lombok.RequiredArgsConstructor;
import mins.study.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "mins.study")
@RequiredArgsConstructor
public class CoreConfiguration {

    private final Map<String, WebSocketHandler> webHandlers;

    @PostConstruct
    public void init() {
        webHandlers.put("/my", new MyWebSocketHandler());
    }
}
