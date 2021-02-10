package com.jskim.book.springboot.service;

import com.jskim.book.springboot.domain.posts.Posts;
import com.jskim.book.springboot.domain.posts.PostsRepository;
import com.jskim.book.springboot.web.dto.PostsListResponseDto;
import com.jskim.book.springboot.web.dto.PostsResponseDto;
import com.jskim.book.springboot.web.dto.PostsSaveRequestDto;
import com.jskim.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor  // final 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성(lombok)
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id"+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지, 조회속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // Posts --> new PostsListResponseDto(posts)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        postsRepository.delete(posts); // JpaRepository 에서 delete 메소드 지원함
    }

}
