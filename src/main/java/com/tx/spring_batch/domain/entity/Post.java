package com.tx.spring_batch.domain.entity;

import com.tx.spring_batch.domain.dto.request.CreatePostRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static Post toEntity(CreatePostRequest postRequest){
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .status(Status.ACTIVE)
                .build();
    }

    public void delete() {
        this.status = Status.DEAD;
        this.deletedAt = LocalDateTime.now();
    }
}
