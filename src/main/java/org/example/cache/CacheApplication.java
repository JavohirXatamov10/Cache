package org.example.cache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.cache.entity.Attachment;
import org.example.cache.entity.Post;
import org.example.cache.repo.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
@EnableCaching
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);}

    @Bean
    public ApplicationRunner applicationRunner(ObjectMapper objectMapper, PostRepository postRepository) {
        return args -> {
            Post[] posts=objectMapper.readValue(new URL("https://jsonplaceholder.typicode.com/posts"), Post[].class);
            for (Post post : posts) {
                File file=new File("photos/image.png");
                InputStream inputStream=new FileInputStream(file);
                post.setPhoto(Attachment.builder()
                        .photo(inputStream.readAllBytes())
                        .build());}
            postRepository.saveAll(Arrays.asList(posts));
        };
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(List.of("posts"));
        return cacheManager;
    }
}
