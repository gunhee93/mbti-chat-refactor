package pjsassy.mbtichatclon.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pjsassy.mbtichatclon.post.domain.Post;

import java.awt.print.Pageable;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p" +
            " join fetch p.user u" +
            " join fetch p.category c" +
            " order by p.count desc, p.id desc"
    , countQuery = "select count(p) from Post p")
    Page<Post> findAllByViewed(Pageable pageable);

    @Query(value = "select p from Post p" +
            " join fetch p.user u" +
            " join fetch p.category c" +
            " order by p.createdAt desc",
    countQuery = "select count(p) from Post p")
    Page<Post> findAllByNewest(Pageable pageable);
}
