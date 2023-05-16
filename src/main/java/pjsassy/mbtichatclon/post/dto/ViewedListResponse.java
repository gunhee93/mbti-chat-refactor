package pjsassy.mbtichatclon.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ViewedListResponse {

    private List<ViewedPostDto> viewedList;
}
