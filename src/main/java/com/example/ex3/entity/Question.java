package com.example.ex3.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String answer;
    private String author;
    private Date date; // date 필드 추가

    public Question(Long id, String question, String answer, String author, Date date) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.author = author;
        this.date = date;
    }

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAnsweredDate(Date date) {
    }
}