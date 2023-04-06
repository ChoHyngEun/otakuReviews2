package com.example.ex3.service;

import com.example.ex3.entity.Question;
import com.example.ex3.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public void addAnswer(Long id, String answer) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question != null) {
            question.setAnswer(answer);
            questionRepository.save(question);
        }
    }

    @Override
    public void addQuestion(Question question) {
        question.setDate(new Date()); // 현재 시간으로 date 필드 설정
        questionRepository.save(question);
    }

}