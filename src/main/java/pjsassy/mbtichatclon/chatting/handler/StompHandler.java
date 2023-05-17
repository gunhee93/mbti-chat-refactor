package pjsassy.mbtichatclon.chatting.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import pjsassy.mbtichatclon.chatting.service.ChatService;

@Component
public class StompHandler implements ChannelInterceptor {

    private ChatService chatService;

    public StompHandler(@Lazy ChatService chatService) {
        this.chatService =chatService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            chatService.exitWaiting(sessionId);
            chatService.exitChattingRoom(sessionId);
        }

        return message;
    }
}
