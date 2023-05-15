package pjsassy.mbtichatclon.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    private Long userId;
    private Long postId;
    private String comment;
}
