package com.example.ex3.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@DynamicUpdate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_username", referencedColumnName = "username")
    private User author;

    private LocalDateTime createdAt;

    @ManyToOne
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment(Long id, String content, User author, LocalDateTime createdAt, Post post) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.post = post;
    }

    public Comment() {
    }

    public String getAuthorUsername() {
        if (author != null) {
            return author.getUsername();
        }
        return null;
    }
}