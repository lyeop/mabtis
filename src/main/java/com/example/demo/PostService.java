package com.example.demo;

import com.example.demo.mapper.PostMapper;
import com.example.demo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;
    public void createPost(Post post) {
        postMapper.createPost(post);
    }
    public List<Post> getAllPosts() {
        return postMapper.findAllPosts();
    }
    public void deletePost(int id) {
        postMapper.deletePost(id);
    }

    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }
    public Post getPostById(int id) {
        return postMapper.findPostById(id);
    }
}
