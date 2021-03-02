package mins.study.web;

import lombok.RequiredArgsConstructor;
import mins.study.websocket.EchoWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import reactor.core.publisher.Flux;

import java.util.Map;

@Controller

public class Welcome {

    @Autowired
    private Map<String, WebSocketHandler> webHandlerRoutingManager;

    @Autowired
    @Lazy
    private HandlerMapping handlerMapping;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/addRoom")
    public ResponseEntity<Object> createRoom(@RequestParam("roomName") String roomName) {
        if(webHandlerRoutingManager.containsKey(roomName)) {
            return ResponseEntity.badRequest().body("Already Exist Room");
        } else {
            webHandlerRoutingManager.put("/" + roomName, new EchoWebSocketHandler());
            ((SimpleUrlHandlerMapping)handlerMapping).setUrlMap(webHandlerRoutingManager);
            return ResponseEntity.ok().body("Created Room!!!");
        }
    }
}
