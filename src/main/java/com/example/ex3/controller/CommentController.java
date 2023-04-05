package com.example.ex3.controller;

import com.example.ex3.model.Comment;
import com.example.ex3.model.Post;
import com.example.ex3.model.User;
import com.example.ex3.service.CommentService;
import com.example.ex3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable Long postId, @ModelAttribute Comment comment, HttpSession session) {
        Post post = postService.getPostById(postId);
        comment.setPost(post);
        User user = (User) session.getAttribute("user");
        comment.setAuthor(user);
        commentService.saveComment(comment);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}/comments/{commentId}/edit")
    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, Model model, HttpSession session) {
        Comment comment = commentService.getCommentById(commentId);
        Post post = postService.getPostById(postId);
        User sessionUser = (User) session.getAttribute("user");
        if (!comment.getAuthorUsername().equals(sessionUser.getUsername())) {
            return "redirect:/posts/" + postId;
        }
        model.addAttribute("comment", comment);
        model.addAttribute("post", post);
        return "comments/edit";
    }

    @PostMapping("/posts/{postId}/comments/{commentId}")
    public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @ModelAttribute Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Comment existingComment = commentService.getCommentById(commentId);
        comment.setAuthor(user);
        comment.setId(commentId);
        comment.setPost(existingComment.getPost());
        commentService.saveComment(comment);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/del/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Comment existingComment = commentService.getCommentById(commentId);
        commentService.deleteCommentById(commentId);
        return "redirect:/posts/" + postId;
    }


}