package pjsassy.mbtichatclon.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.post.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(String category);
}
