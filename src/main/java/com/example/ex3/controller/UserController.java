package com.example.ex3.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.ex3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ex3.model.User;
import com.example.ex3.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        // 6자리 랜덤 숫자 생성 및 삽입
        Random rand = new Random();
        int otp = rand.nextInt(900000) + 100000; // 100000~999999 사이의 랜덤 숫자 생성
        user.setOtpSecret(Integer.toString(otp));

        userRepository.save(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }
    @GetMapping("/main")
    public String MainForm(Model model) {
        return "main";
    }
    @GetMapping("/index")
    public String IndexForm(Model model) {
        return "index";
    }



    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // 사용자 인증 처리
        User user = userService.authenticate(username, password);
        if (user == null) {
            // 로그인 실패 시 로그인 페이지로 이동
            return "redirect:/login";
        }
        // 사용자 정보 세션에 저장
        session.setAttribute("user", user);
        // OTP 인증 페이지로 이동
        return "redirect:/otp-auth";
    }
    @GetMapping("/otp-auth")
    public String otpAuthForm() {
        return "otp-auth";
    }

    @PostMapping("/otp-auth")
    public String otpAuth(@RequestParam String name, @RequestParam String otpSecret, HttpSession session, Model model) {
        User user = userRepository.findByName(name);
        if (user != null && user.getOtpSecret().equals(otpSecret)) {
            session.setAttribute("user", user);
            return "redirect:/main";
        } else {
            model.addAttribute("error", "OTP 번호가 일치하지 않습니다.");
            return "otp-auth";
        }
    }

    // 10초마다 OtpSecret 필드에 랜덤한 값 삽입
    @Scheduled(fixedDelay = 60000) // 60초마다 실행
    public void changeOtpSecret() {
        Iterable<User> users = userRepository.findAll();
        Random rand = new Random();
        for (User user : users) {
            int otp = rand.nextInt(900000) + 100000; // 100000~999999 사이의 랜덤 숫자 생성
            user.setOtpSecret(Integer.toString(otp));
            userRepository.save(user);
        }
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}