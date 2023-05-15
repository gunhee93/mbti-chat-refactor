package pjsassy.mbtichatclon.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
