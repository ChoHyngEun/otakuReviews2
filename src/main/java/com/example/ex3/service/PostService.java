package com.example.ex3.service;

import com.example.ex3.model.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(Long id);

    Post save(Post post);

    void deleteById(Long id);
}