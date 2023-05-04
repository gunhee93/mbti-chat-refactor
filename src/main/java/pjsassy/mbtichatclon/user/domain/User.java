package pjsassy.mbtichatclon.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String nickname;
    private String gender;
    private String mbti;
    private String image;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(Long id, String loginId, String password, String nickname, String gender, String mbti, String image, String email, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.mbti = mbti;
        this.gender = gender;
        this.image = image;
        this.email = new Email(email);
        this.role = Role.ROLE_USER;
    }

    public static User of(Long id, String loginId, String password, String nickname, String mbti, String gender, String image, String email) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .gender(gender)
                .mbti(mbti)
                .image(image)
                .email(email)
                .build();
    }
}
