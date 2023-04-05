package com.example.ex3.controller;

import com.example.ex3.model.Comment;
import com.example.ex3.model.Post;
import com.example.ex3.model.User;
import com.example.ex3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/st")
    public String stList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("post", new Post()); // post 객체 추가
        model.addAttribute("posts", posts);
        return "posts/st";
    }

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("post", new Post()); // post 객체 추가
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/posts/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/new";
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        post.setAuthor(user);
        postService.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "posts/show";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model, HttpSession session) {
        Post post = postService.getPostById(id);
        User user = (User) session.getAttribute("user");
        if (user == null || !post.getAuthor().getId().equals(user.getId())) {
            // User is not logged in or is not the author of the post
            return "redirect:/posts/" + id;
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !post.getAuthor().getId().equals(user.getId())) {
            // User is not logged in or is not the author of the post
            return "redirect:/posts/" + id;
        }
        post.setId(id);
        postService.savePost(post);
        return "redirect:/posts/" + id;
    }


    @PostMapping("/posts/{id}")
    public String updatePost2(@PathVariable Long id, @ModelAttribute Post post,  HttpSession session) {
        User user = (User) session.getAttribute("user");
        post.setAuthor(user); // 로그인한 사용자 정보를 포스트 객체에 추가
        post.setId(id);
        postService.savePost(post);
        return "redirect:/posts/" + id;
    }


    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }

}