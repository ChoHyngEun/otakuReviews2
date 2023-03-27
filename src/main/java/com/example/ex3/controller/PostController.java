package com.example.ex3.controller;

import com.example.ex3.model.Post;
import com.example.ex3.model.User;
import com.example.ex3.service.PostService;
import com.example.ex3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/post")
    public String showAllPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/postList";
    }

    @GetMapping("/post/write")
    public String showWriteForm(Post post) {
        return "post/writeForm";
    }

    @PostMapping("/post/write")
    public String writePost(@Valid Post post, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "post/writeForm";
        }
        User user = (User) session.getAttribute("user");
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/post";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            return "redirect:/post";
        }
        model.addAttribute("post", post);
        return "post/postView";
    }

    @GetMapping("/post/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.findById(id);
        if (post == null || !post.getUser().equals(user)) {
            return "redirect:/post";
        }
        model.addAttribute("post", post);
        return "post/editForm";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, @Valid Post post, BindingResult bindingResult, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post existingPost = postService.findById(id);
        if (existingPost == null || !existingPost.getUser().equals(user)) {
            return "redirect:/post";
        }
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        postService.savePost(existingPost);
        return "redirect:/post/" + id;
    }

    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.findById(id);
        if (post == null || !post.getUser().equals(user)) {
            return "redirect:/post";
        }
        postService.deletePost(id);
        return "redirect:/post";
    }
}