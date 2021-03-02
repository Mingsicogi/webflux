package mins.study.config;

import lombok.extern.slf4j.Slf4j;
import mins.study.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class WebSocketConfiguration {

    @PostConstruct
    public void init() {
        log.info("### WebSocketConfiguration Finished ###");
    }

    /**
     * topic 별 web handler 관리를 위한 싱글톤 빈
     *
     * @return
     */
    @Bean
    public Map<String, WebSocketHandler> webHandlers() {
        return new HashMap<>();
    }

    /**
     * web handler 관리를 위한 handler mapping
     *
     * @param webHandlers
     * @return
     */
    @Bean
    public HandlerMapping handlerMapping(Map<String, WebSocketHandler> webHandlers) {

        webHandlers.put("/my", new MyWebSocketHandler());

        return new SimpleUrlHandlerMapping(webHandlers);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        TomcatRequestUpgradeStrategy strategy = new TomcatRequestUpgradeStrategy();
        strategy.setMaxSessionIdleTimeout(0L);
        return new HandshakeWebSocketService(strategy);
    }
}
