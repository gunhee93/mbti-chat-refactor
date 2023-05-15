package pjsassy.mbtichatclon.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.post.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
