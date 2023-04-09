package com.example.ex3.controller;

import com.example.ex3.model.Comment;
import com.example.ex3.model.Post;
import com.example.ex3.model.User;
import com.example.ex3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/st")
    public String stList(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return null;
        }

        List<Post> posts = postService.getAllPostsByCategory(Post.Category.STARBUCKS.toString());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);
        model.addAttribute("category", Post.Category.STARBUCKS);
        return "posts/list";
    }
    @GetMapping("/mg")
    public String mgList(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return null;
        }

        List<Post> posts = postService.getAllPostsByCategory(Post.Category.MEGA.toString());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);
        model.addAttribute("category", Post.Category.MEGA);
        return "posts/list";
    }

    @GetMapping("/pk")
    public String pkList(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return null;
        }
        List<Post> posts = postService.getAllPostsByCategory(Post.Category.PAIK.toString());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);
        model.addAttribute("category", Post.Category.PAIK);
        return "posts/list";
    }

    @GetMapping("/ed")
    public String edList(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return null;
        }
        List<Post> posts = postService.getAllPostsByCategory(Post.Category.EDIYA.toString());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", posts);
        model.addAttribute("category", Post.Category.EDIYA);
        return "posts/list";
    }





    @GetMapping("/etc")
    public String etcList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("post", new Post()); // post 객체 추가
        model.addAttribute("posts", posts);
        model.addAttribute("category", Post.Category.OTHER);
        return "posts/list";
    }
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getPostList(Model model,  @RequestParam(name = "category", required = false) Post.Category category) {
        List<Post> posts = postService.getAllPosts();
        if (category == null || category == Post.Category.DEFAULT) {
            posts = postService.getAllPosts();
        } else {
            posts = postService.getPostsByCategory(category);
        }
        model.addAttribute("post", new Post()); // post 객체 추가
        model.addAttribute("posts", posts);
        model.addAttribute("categories", Post.Category.values()); // 카테고리 리스트 추가
        return "posts/list";
    }

    @GetMapping("/posts/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", Post.Category.values()); // 카테고리 리스트 추가
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

    @GetMapping("/new/{category}")
    public String createReviewForm(@PathVariable String category, Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("category", category);
        return "posts/new";
    }





}