package mins.study.mvc.rx;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessengerController {

    public static Map<String, List<Observer<Message>>> chattingRoomList;
    public static Messenger messenger;
    static {
        chattingRoomList = new HashMap<>();
        messenger = new Messenger();
    }

    @GetMapping("/create/chatting/room")
    public ResponseEntity<Object> createChattingRoom(@RequestParam String roomNumber) {
        if (chattingRoomList.containsKey(roomNumber)) {
            return ResponseEntity.badRequest().body("Already Exist Room");
        } else {
            chattingRoomList.put(roomNumber, new ArrayList<>());
            return ResponseEntity.ok().body("Created room.");
        }
    }

    @GetMapping("/enter/chatting/room")
    public ResponseEntity<Object> enter(@RequestParam String roomNumber, @RequestParam String id) {
        if(chattingRoomList.containsKey(roomNumber)) {
            Observer<Message> listener = messenger.listener(id);
            List<Observer<Message>> observers = chattingRoomList.get(roomNumber);
            observers.add(listener);

            return ResponseEntity.ok().body("Entering " + roomNumber);
        } else {
            return ResponseEntity.badRequest().body("Not Exist Room");
        }
    }

    @GetMapping("/send/message")
    public ResponseEntity<Object> sendMessage(String roomNumber, String message, String fromId) {
        if(chattingRoomList.containsKey(roomNumber)) {
            chattingRoomList.get(roomNumber).forEach(observer -> messenger.sendMessage(message, fromId).subscribe(observer));
            return ResponseEntity.ok().body("Message sent successfully");
        } else {
            return ResponseEntity.badRequest().body("Not Exist Room");
        }
    }
}
