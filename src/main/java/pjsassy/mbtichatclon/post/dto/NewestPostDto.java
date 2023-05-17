package pjsassy.mbtichatclon.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewestPostDto {

    private Long postId;
    private String title;
    private String nickName;
    private String category;
    private String writeTime;

}
