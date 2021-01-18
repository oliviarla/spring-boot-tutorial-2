package com.oliviarla.springboot.web;

import com.oliviarla.springboot.service.posts.PostsService;
import com.oliviarla.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index"; //resources/templates/index.mustache 실행
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; //resources/templates/posts-save.mustache 실행
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}
