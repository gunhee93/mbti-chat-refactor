package pjsassy.mbtichatclon.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.chatting.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
