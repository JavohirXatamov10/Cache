package org.example.cache.controller;
import lombok.RequiredArgsConstructor;
import org.example.cache.entity.Attachment;
import org.example.cache.entity.Post;
import org.example.cache.repo.PostRepository;
import org.example.cache.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    @GetMapping
    public String sentToMainPage(Model model) {
        long start = System.currentTimeMillis();
        model.addAttribute("posts",postRepository.findAll());
        long finish = System.currentTimeMillis();
        System.out.println("No Cashe:"+(finish-start));
        return "mainPage";
    }

    @GetMapping("/cache")
    public String sendCashedDataToMainPage(Model model) {
        long start = System.currentTimeMillis();
        model.addAttribute("posts",postService.findALl());
        long finish = System.currentTimeMillis();
        System.out.println("Cache:"+(finish-start));
        return "mainPage";

    }

    @GetMapping("photo/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getPostPhoto(@PathVariable Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Attachment photo = post.getPhoto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo.getPhoto(), headers, HttpStatus.OK);
    }


}
