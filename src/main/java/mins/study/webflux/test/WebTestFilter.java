package mins.study.webflux.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WebTestFilter implements WebFilter {

    private static int counter = 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        if(counter >= 5) {
            counter = 0;
            throw new RuntimeException("counter is more than 5");
        } else {
            log.info("request count : {}", counter++);
        }

        return chain.filter(exchange);
    }
}
