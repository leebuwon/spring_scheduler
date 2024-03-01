package com.tx.spring_batch.domain.service;

import com.tx.spring_batch.domain.dto.request.CreatePostRequest;
import com.tx.spring_batch.domain.entity.Post;
import com.tx.spring_batch.domain.entity.Status;
import com.tx.spring_batch.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableScheduling
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void create(CreatePostRequest createPostRequest) {
        Post post = Post.toEntity(createPostRequest);
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.delete();

        log.info("post status = {}", post.getStatus());
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?") // 매 새벽 1시 마다 일괄 제거
    public void performHardDelete() {
        List<Post> softDeletedPosts = postRepository
                .findByStatusAndDeletedAtBefore(Status.DEAD, LocalDateTime.now().minusDays(7)); // DeletedAt을 기준으로 7일이 지난 게시글 삭제

        postRepository.deleteAll(softDeletedPosts);
    }
}
