package mins.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfiguration {

    /**
     * topic 별 web handler 관리를 위한 싱글톤 빈
     *
     * @return
     */
    @Bean
    public Map<String, WebSocketHandler> webHandlers() {
        return new HashMap<>();
    }

    @Bean
    public HandlerMapping handlerMapping(Map<String, WebSocketHandler> webHandlers) {
        return new SimpleUrlHandlerMapping(webHandlers);
    }
}
