package pjsassy.mbtichatclon.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.chatting.domain.ChattingRoom;

public interface ChattingRepository extends JpaRepository<ChattingRoom, Long> {
}
