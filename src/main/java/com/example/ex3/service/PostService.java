package com.example.ex3.service;

import com.example.ex3.model.Post;
import com.example.ex3.model.User;
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

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> findAllByDate() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }
}
