package com.example.demo.mapper;

import com.example.demo.model.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface PostMapper {
    void createPost(Post post);
    List<Post> findAllPosts();
    void deletePost(@Param("id") int id);

    void updatePost(Post post);
    Post findPostById(@Param("id") int id);

}

