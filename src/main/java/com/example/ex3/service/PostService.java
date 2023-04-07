package com.example.ex3.service;

import com.example.ex3.model.Post;
import com.example.ex3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getPostsByCategory(Post.Category category) {
        return postRepository.findAllByCategory(category);
    }

    public List<Post> getAllPostsByCategory(String category) {
        Post.Category postCategory = Post.Category.valueOf(category.toUpperCase());
        return postRepository.findAllByCategory(postCategory);
    }


}