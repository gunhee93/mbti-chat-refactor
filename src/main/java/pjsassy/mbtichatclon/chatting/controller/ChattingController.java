package pjsassy.mbtichatclon.chatting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import pjsassy.mbtichatclon.chatting.dto.MessageRequest;
import pjsassy.mbtichatclon.chatting.dto.WaitingRequest;
import pjsassy.mbtichatclon.chatting.service.ChatService;
import pjsassy.mbtichatclon.chatting.service.MessageService;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final MessageService messageService;
    private final ChatService chatService;

    @MessageMapping("/chat/wait")
    public void addUserToWaiting(WaitingRequest waitingRequest, @Header("simpSessionId") String sessionId) {
        chatService.matchUserWithMbti(waitingRequest, sessionId);
    }

    @MessageMapping("/chat/match/{roomId}")
    public void sendMessage(@DestinationVariable("roomId") Long roomId, MessageRequest messageRequest) {
        messageService.sendMessage(roomId, messageRequest);
    }
}
