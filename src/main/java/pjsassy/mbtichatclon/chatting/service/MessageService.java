package pjsassy.mbtichatclon.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.chatting.domain.ChattingRoom;
import pjsassy.mbtichatclon.chatting.domain.Message;
import pjsassy.mbtichatclon.chatting.dto.MessageRequest;
import pjsassy.mbtichatclon.chatting.dto.MessageResponse;
import pjsassy.mbtichatclon.chatting.repository.MessageRepository;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChattingService chattingService;

    @Transactional
    public void sendMessage(Long roomId, MessageRequest messageRequest) {
        ChattingRoom room = chattingService.findRoomById(roomId);
        User user = userService.findById(Long.valueOf(messageRequest.getSendUserId()));
        String content = messageRequest.getContent();

        Message message = Message.of(room, user, content);
        Message savedMessage = messageRepository.save(message);

        LocalDateTime createdAt = message.getCreatedAt();
        String time = createdAt.format(DateTimeFormatter.ofPattern("HH:mm"));

        MessageResponse messageResponse = new MessageResponse(
                room.getId(), user.getId(), content, time, user.getNickname(), savedMessage.getId());
        simpMessageSendingOperations.convertAndSend("/sub/chat/match/" + roomId, messageResponse);
    }
}
