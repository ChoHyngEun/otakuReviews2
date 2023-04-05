package com.example.ex3.service;

import com.example.ex3.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestionById(Long id);

    void addQuestion(Question question);

    void addAnswer(Long id, String answer);

}