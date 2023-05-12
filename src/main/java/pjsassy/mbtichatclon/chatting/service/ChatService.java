package pjsassy.mbtichatclon.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.chatting.data.MatchingMbtiData;
import pjsassy.mbtichatclon.chatting.dto.ChatCloseResponse;
import pjsassy.mbtichatclon.chatting.dto.MatchResponse;
import pjsassy.mbtichatclon.chatting.dto.RoomInformation;
import pjsassy.mbtichatclon.chatting.dto.WaitingRequest;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.service.UserService;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    public ConcurrentHashMap<Long, String> waiting = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, MatchingMbtiData> waitingData = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Long> chattingRoomSessions = new ConcurrentHashMap<>();

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final UserService userService;
    private final ChattingService chattingService;

    @Transactional
    public void matchUserWithMbti(WaitingRequest waitingRequest, String sessionId) {
        Long userId = Long.valueOf(waitingRequest.getUserId());
        waiting.put(userId, sessionId);

        User user = userService.findById(userId);
        String myMbti = user.getMbti();
        String selectMbti = waitingRequest.getSelectMbti();

        MatchingMbtiData matchingMbtiData = new MatchingMbtiData(myMbti, selectMbti);
        waitingData.put(sessionId, matchingMbtiData);

        if (waiting.size() > 1) {
            startMatching(user, myMbti, selectMbti);
        }
    }

    @Transactional
    private void startMatching(User user, String myMbti, String selectMbti) {
        for (Long waitingUserId : waiting.keySet()) {
            Long userId = user.getId();

            if (userId == waitingUserId) continue;

            String findSessionId = waiting.get(waitingUserId);
            MatchingMbtiData waitingMatchingMbtiData = waitingData.get(findSessionId);
            String waitingUserMbti = waitingMatchingMbtiData.getMyMbti();
            String waitingUserSelectMbti = waitingMatchingMbtiData.getSelectMbti();

            if (selectMbti.equals(waitingUserMbti) && myMbti.equals(waitingUserSelectMbti)) {
                succeedMatching(user, waitingUserId, userId);
                break;
            }
        }
    }

    private void succeedMatching(User user, Long waitingUserId, Long userId) {
        RoomInformation roomInformation = chattingService.createChattingRoom(user, waitingUserId);
        Long roomId = roomInformation.getRoomId();

        MatchResponse myMatchResponse = new MatchResponse(
                "match", String.valueOf(roomId), roomInformation.getMatchedUserNickname());
        MatchResponse matchedUserResponse = new MatchResponse(
                "match", String.valueOf(roomId), user.getNickname());

        simpMessageSendingOperations.convertAndSend("/sub/chat/wait/" + userId, myMatchResponse);
        simpMessageSendingOperations.convertAndSend("/sub/chat/wait/" + waitingUserId, matchedUserResponse);

        chattingRoomSessions.put(waiting.get(userId), roomId);
        chattingRoomSessions.put(waiting.get(waitingUserId), roomId);

        waitingData.remove(userId);
        waitingData.remove(waitingUserId);

    }

    public void exitWaiting(String sessionId) {
        if (waitingData.get(sessionId) != null) {
            waitingData.remove(sessionId);

            for (Long userId : waiting.keySet()) {
                if (waiting.get(userId).equals(sessionId)) {
                    waiting.remove(userId);
                    break;
                }
            }
        }
    }
    public void exitChattingRoom(String sessionId) {
        if (chattingRoomSessions.get(sessionId) != null) {
            Long roomId = chattingRoomSessions.get(sessionId);
            ChatCloseResponse chatCloseResponse = new ChatCloseResponse(roomId);
            simpMessageSendingOperations.convertAndSend("/sub/shat/match/" + roomId, chatCloseResponse);
            chattingRoomSessions.remove(sessionId);
        }
    }
}
