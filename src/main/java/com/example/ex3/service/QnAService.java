package com.example.ex3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class QnAService {
    private static Map<String, String> qna = new HashMap<>();

    public QnAService() {
        // 데이터 초기화
        qna.put("질문1", "답변1");
        qna.put("질문2", "답변2");
        qna.put("질문3", "답변3");
    }

    public String getAnswer(String question) {
        String answer = qna.get(question);
        if (answer == null) {
            answer = "해당 질문에 대한 답변을 찾을 수 없습니다.";
        }
        return answer;
    }

    public static List<String> getAllQuestions() {
        return new ArrayList<>(qna.keySet());
    }
}