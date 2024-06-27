package com.example.demo;



import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserMapper userMapper;
    public User register(User user) {
        userMapper.register(user);
        return user;
    }
    public User login(String username, String password) {
        return userMapper.findByUsername(username);
    }
}
