package pjsassy.mbtichatclon.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pjsassy.mbtichatclon.post.dto.CreatePostRequest;
import pjsassy.mbtichatclon.post.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public ResponseEntity createPost(@RequestBody CreatePostRequest createPostRequest) {
        postService.createPost(createPostRequest);
    }
}
