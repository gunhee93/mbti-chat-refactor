package pjsassy.mbtichatclon.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pjsassy.mbtichatclon.post.dto.CreatePostRequest;
import pjsassy.mbtichatclon.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String content;
    private int count;

    @CreatedDate
    @LastModifiedDate
    private LocalDateTime createdAt;

    @Builder
    public Post(User user, List<Comment> comments, Category category, String title,
                String content, LocalDateTime createdAt) {

        this.user = user;
        this.comments = comments;
        this.category = category;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Post of(CreatePostRequest createPostRequest, User user, Category category) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .category(category)
                .user(user).build();
    }

    public void addPostCount() {
        this.count++;
    }
}
