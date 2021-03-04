package com.jskim.book.springboot.web;

import com.jskim.book.springboot.config.auth.LoginUser;
import com.jskim.book.springboot.config.auth.dto.SessionUser;
import com.jskim.book.springboot.service.PostsService;
import com.jskim.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        // postsService.findAllDesc() 로 가져온 결과를 posts 로 index.mustache 에 전달함

        // SessionUser user = (SessionUser) httpSession.getAttribute("user"); --> @LoginUser 로 세션정보 가져옴
        // CustomOAuth2UserService 에서 로그인 성공 시, 세션에 SessionUser 저장

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}