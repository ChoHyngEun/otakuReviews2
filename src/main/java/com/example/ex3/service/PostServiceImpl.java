package com.example.ex3.service;

//import com.example.ex3.exception.ResourceNotFoundException;
import com.example.ex3.model.Post;
import com.example.ex3.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.lang.IllegalArgumentException;
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));
    }

    @Override
    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}