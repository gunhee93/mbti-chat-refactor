package pjsassy.mbtichatclon.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.chatting.domain.ChattingRoom;
import pjsassy.mbtichatclon.chatting.dto.RoomInformation;
import pjsassy.mbtichatclon.chatting.repository.ChattingRepository;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;
    private final UserService userService;



    @Transactional
    public RoomInformation createChattingRoom(User sendUser, Long waitingUserId) {
        User receiveUser = userService.findById(waitingUserId);
        ChattingRoom chattingRoom = chattingRepository.save(new ChattingRoom(sendUser, receiveUser));
        return new RoomInformation(chattingRoom.getId(), receiveUser.getNickname());
    }

    public ChattingRoom findRoomById(Long roomId) {
        ChattingRoom chattingRoom = chattingRepository.findById(roomId).orElseThrow(() -> {
            throw new CustomIllegalArgumentException(ErrorCode.NOT_FOUND_ROOM);
        });
        return chattingRoom;
    }
}
