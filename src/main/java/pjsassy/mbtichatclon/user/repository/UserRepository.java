package pjsassy.mbtichatclon.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.dto.DuplicateEmailRequest;
import pjsassy.mbtichatclon.user.dto.DuplicateLoginIdRequest;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(DuplicateLoginIdRequest duplicateLoginIdRequest);

    Optional<User> findByEmail(DuplicateEmailRequest duplicateEmailRequest);
}
