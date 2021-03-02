package mins.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

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
    public Map<String, WebSocketHandler> webHandlerRoutingManager() {
        return new HashMap<>();
    }

    /**
     * web handler 관리를 위한 handler mapping
     *
     * @param webHandlerRoutingManager
     * @return
     */
    @Bean
    public HandlerMapping handlerMapping(Map<String, WebSocketHandler> webHandlerRoutingManager) {
        log.info("### setting handler ###");
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(webHandlerRoutingManager);
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }
}
