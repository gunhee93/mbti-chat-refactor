package pjsassy.mbtichatclon.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pjsassy.mbtichatclon.post.dto.*;
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
            @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        List<ViewedPostDto> viewedPost = postService.findViewedPost(pageable);
        ViewedListResponse viewedListResponse = new ViewedListResponse(viewedPost);

        return new ResponseEntity(viewedListResponse, HttpStatus.OK);
    }

    // 게시글 목록 (최신순)
    @GetMapping("/home/newest")
    public ResponseEntity<NewestListResponse> postListNewest(
            @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        List<NewestPostDto> newestPost = postService.findNewestPost(pageable);
        NewestListResponse newestListResponse = new NewestListResponse(newestPost);

        return new ResponseEntity(newestListResponse, HttpStatus.OK);
    }
}
