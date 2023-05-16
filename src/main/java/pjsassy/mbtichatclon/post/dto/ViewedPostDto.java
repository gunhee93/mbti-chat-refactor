package pjsassy.mbtichatclon.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pjsassy.mbtichatclon.post.domain.Category;

@Getter
@AllArgsConstructor
public class ViewedPostDto {

    private Long postId;
    private String title;
    private String nickName;
    private String category;
    private String writeTime;
}
