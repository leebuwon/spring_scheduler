package com.tx.spring_batch.domain.repository;

import com.tx.spring_batch.domain.entity.Post;
import com.tx.spring_batch.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatusAndDeletedAtBefore(Status status, LocalDateTime localDateTime);
}
