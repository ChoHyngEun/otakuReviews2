package com.example.ex3.repository;

import com.example.ex3.model.Comment;
import com.example.ex3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthor(User author);
}