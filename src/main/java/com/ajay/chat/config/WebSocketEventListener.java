package com.ajay.chat.config;

import com.ajay.chat.dto.ChatMessage;
import com.ajay.chat.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {   // this class is to listen whenever a user disconnect the chat

    private final SimpMessageSendingOperations messageTemplate;


    /*So if we want to listen to different event , we can create a event listener and pass the required parameter (on which you want to listen)*/
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        /*we want to inform the users of chat application that our user have left the chat*/

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");

        if(username != null){
            log.info("User disconnected: {}",username);

            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // inform others that the user has left the chat
            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
