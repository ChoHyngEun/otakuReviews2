package com.example.ex3.controller;

import com.example.ex3.entity.Question;
import com.example.ex3.model.User;
import com.example.ex3.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String getAllQuestions(Model model) {
        List<Question> questions = questionService.getAllQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("question", new Question());
        return "qna";
    }

    @GetMapping("/{id}")
    public String getQuestionById(@PathVariable Long id, Model model) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return "qna2";
    }

    @PostMapping
    public String addQuestion(@RequestParam("author") String author,
                              @RequestParam("question") String question, HttpSession session) {
        Question newQuestion = new Question();
        User user = (User) session.getAttribute("user");
        newQuestion.setAuthor(author);
        newQuestion.setQuestion(question);
        questionService.addQuestion(newQuestion);
        return "redirect:/qna";
    }

    @PostMapping("/{id}")
    public String addAnswer(@PathVariable Long id, @RequestParam String answer) {
        questionService.addAnswer(id, answer);
        return "redirect:/qna";
    }

}