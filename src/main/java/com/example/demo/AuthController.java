package com.example.demo;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostService postService;


    @GetMapping("/")
    public String index() {
        return "redirect:/login"; // "/" 경로로 접근하면 로그인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        User user = authService.login(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user); // 로그인 성공 시 사용자 정보를 세션에 저장
            return "redirect:/board"; // 로그인 성공 시 게시판 페이지로 이동
        }
        return "redirect:/login?error=true"; // 로그인 실패 시 다시 로그인 페이지로 이동 (선택적으로 에러 메시지를 전달할 수 있음)
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html로 이동
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        authService.register(user);
        return "redirect:/login"; // 회원가입 완료 후 로그인 페이지로 이동
    }


    @GetMapping("/board")
    public String boardPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", currentUser.getUsername());

        List<Post> posts = postService.getAllPosts(); // 게시글 목록을 가져옴
        model.addAttribute("posts", posts); // 게시글 목록을 모델에 추가

        return "board";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute("post") Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        post.setUserId(currentUser.getUsername()); // 현재 로그인한 사용자의 아이디 설정
        postService.createPost(post); // 게시글 생성 메서드 호출
        return "redirect:/board"; // 게시글 생성 후 게시판 페이지로 리다이렉트
    }
    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("id") int id) {
        postService.deletePost(id);
        return "redirect:/board";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }
    @GetMapping("/editPost/{id}")
    public String editPostForm(@PathVariable("id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("postToEdit", post);
        List<Post> posts = postService.getAllPosts(); // 업데이트된 게시물 목록을 다시 가져옴
        model.addAttribute("posts", posts); // 게시물 목록을 모델에 추가
        return "editPost"; // 수정 폼을 표시할 HTML 페이지로 이동
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") Post post) {
        postService.updatePost(post);
        return "redirect:/board"; // 수정 완료 후 게시판 페이지로 리다이렉트
    }



}