package pjsassy.mbtichatclon.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequest {

    private String title;
    private String content;
    private Long userId;
    private String categoryName;

}
