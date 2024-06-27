package com.example.demo.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime createdAt;

    // getters and setters
}
