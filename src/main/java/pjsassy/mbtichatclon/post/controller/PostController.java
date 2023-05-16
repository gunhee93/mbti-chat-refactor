package pjsassy.mbtichatclon.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pjsassy.mbtichatclon.post.dto.CreatePostRequest;
import pjsassy.mbtichatclon.post.dto.CreatePostResponse;
import pjsassy.mbtichatclon.post.dto.ViewedListResponse;
import pjsassy.mbtichatclon.post.dto.ViewedPostDto;
import pjsassy.mbtichatclon.post.service.PostService;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/new")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest createPostRequest) {
        Long savedPost = postService.createPost(createPostRequest);
        CreatePostResponse createPostResponse = new CreatePostResponse(savedPost);

        return new ResponseEntity(createPostResponse, HttpStatus.OK);
    }

    // 게시글 목록 (조회순)
    @GetMapping("/home/viewed")
    public ResponseEntity<ViewedListResponse> postListViewed(
            @PageableDefault(size = 10, sort = {"id"})Pageable pageable){
        List<ViewedPostDto> viewedPost = postService.findViewedPost(pageable);
        ViewedListResponse viewedListResponse = new ViewedListResponse(viewedPost);

        return new ResponseEntity(viewedListResponse, HttpStatus.OK);
    }
}
