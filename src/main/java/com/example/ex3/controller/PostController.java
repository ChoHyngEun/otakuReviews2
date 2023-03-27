package com.example.ex3.controller;

import com.example.ex3.model.Post;
import com.example.ex3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "posts/view";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "posts/new";
    }

    @PostMapping("")
    public String save(@Valid Post post, BindingResult result) {
        if (result.hasErrors()) {
            return "posts/new";
        }
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@Valid Post post, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return "posts/edit";
        }
        post.setId(id);
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
