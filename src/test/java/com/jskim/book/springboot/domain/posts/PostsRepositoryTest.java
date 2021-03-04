package com.jskim.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void Save_Load() {
        //given
        String title = "Test Title";
        String content = "Test Content";

        // 'posts' 테이블에 insert/update 쿼리 실행(id 있으면 update, 없으면 insert)
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jskim1212@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll(); // 'posts' 테이블에 있는 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_Register() {
        //given
        LocalDateTime now = LocalDateTime.of(2021,2,16,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> createdDate="+posts.getCreatedData()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedData()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}
