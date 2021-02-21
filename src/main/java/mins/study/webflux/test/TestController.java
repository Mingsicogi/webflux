package mins.study.webflux.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/counter")
    @ResponseBody
    public Mono<String> counter() {
        return Mono.just("OK~").log();
    }
}
