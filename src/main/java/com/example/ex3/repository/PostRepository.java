package com.example.ex3.repository;

import com.example.ex3.model.Post;
import com.example.ex3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);

}