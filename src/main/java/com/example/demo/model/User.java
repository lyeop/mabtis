package com.example.demo.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    // 추가 필드 (이메일 등)
}