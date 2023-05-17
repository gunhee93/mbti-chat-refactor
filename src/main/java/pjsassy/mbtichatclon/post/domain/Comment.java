package pjsassy.mbtichatclon.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pjsassy.mbtichatclon.post.dto.CreateCommentRequest;
import pjsassy.mbtichatclon.user.domain.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    @CreatedDate
    @LastModifiedDate
    private LocalDateTime createAt;

    @Builder
    public Comment(Long id, Post post, User user, String comment, LocalDateTime createAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.comment = comment;
        this.createAt = createAt;
    }

    public static Comment of(CreateCommentRequest createCommentRequest, User user, Post post) {
        return Comment.builder()
                .comment(createCommentRequest.getComment())
                .post(post)
                .user(user)
                .build();
    }
}
