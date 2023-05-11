package pjsassy.mbtichatclon.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.chatting.domain.message.Message;

public interface ChattingRepository extends JpaRepository<ChattingRoom, Long> {
}
