package pjsassy.mbtichatclon.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.post.domain.Category;
import pjsassy.mbtichatclon.post.domain.Post;
import pjsassy.mbtichatclon.post.dto.CreatePostRequest;
import pjsassy.mbtichatclon.post.dto.ViewedPostDto;
import pjsassy.mbtichatclon.post.repository.CategoryRepository;
import pjsassy.mbtichatclon.post.repository.PostRepository;
import pjsassy.mbtichatclon.user.domain.User;
import pjsassy.mbtichatclon.user.service.UserService;

import java.awt.print.Pageable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public Long createPost(CreatePostRequest createPostRequest) {
        User user = userService.findById(createPostRequest.getUserId());
        Category category = categoryRepository.findById(createPostRequest.getCategoryName())
                .orElseThrow(() -> {
                    throw new CustomIllegalArgumentException(ErrorCode.WRONG_CATEGORY);
                });

        Post post = Post.of(createPostRequest, user, category);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    public List<ViewedPostDto> findViewedPost(Pageable pageable) {

        Page<Post> findViewedPost = postRepository.findAllByViewed(pageable);

        return findViewedPost.stream().map(p -> new ViewedPostDto(
                p.getId(), p.getTitle(), p.getUser().getNickname(), p.getCategory().getName(),
                p.getCreatedAt().format(DateTimeFormatter.ofPattern("MM dd HH:mm"))
        )).collect(Collectors.toList());
    }
}
