package com.example.ex3.controller;

import com.example.ex3.entity.Question;
import com.example.ex3.repository.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class QnAController {

    @Autowired
    private QnARepository qnaRepository;

    // QnA 목록을 가져와서 화면에 보여줌
    @GetMapping("/qna")
    public String index(Model model) {
        List<Question> questions = qnaRepository.findAll();
        model.addAttribute("questions", questions);
        return "qna";
    }

    // 새로운 QnA를 추가함
    @PostMapping("/qna")
    public String addQna(@ModelAttribute("newQuestion") Question newQuestion) {
        newQuestion.setDate(new Date()); // 새로운 질문에 현재 날짜를 설정
        qnaRepository.save(newQuestion);
        // ID가 0 미만이면 ID를 재설정
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        return "redirect:/qna";
    }

    // 특정 QnA를 가져와서 보여줌
    @GetMapping("/qna/{questionId}")
    public String getQna(@PathVariable Long questionId, Model model) {
        Question question = qnaRepository.findById(questionId).orElse(new Question());
        if (question.getId() != 0L) {
            model.addAttribute("selectedQuestion", question);
        }
        return "qna2";
    }

    // 모든 QnA를 가져와서 보여줌
    @GetMapping("/qna/all")
    public String getAllQuestions(Model model) {
        List<Question> questions = qnaRepository.findAll();
        model.addAttribute("questions", questions);
        model.addAttribute("newQuestion", new Question());
        return "qna";
    }

    // 기본적인 새로운 질문을 생성함
    @ModelAttribute("newQuestion")
    public Question getDefaultQuestion() {
        return new Question();
    }

}