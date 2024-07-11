package org.example.cache.service;

import lombok.RequiredArgsConstructor;
import org.example.cache.entity.Post;
import org.example.cache.repo.PostRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Cacheable(value = "posts", key = "'post'")
    public List<Post> findALl() {
         return postRepository.findAll();
    }
}
