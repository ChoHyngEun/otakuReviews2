package com.example.ex3.controller;

import java.security.GeneralSecurityException;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.ex3.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.ex3.model.User;
import com.example.ex3.repository.UserRepository;

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 현재 사용자의 세션을 모두 제거
        return "redirect:/"; // 메인 페이지로 리다이렉트
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        // 사용자 인증 처리
        User user = userService.authenticate(username, password);
        if (user == null) {
            // 로그인 실패 시 로그인 페이지로 이동
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            model.addAttribute("popup", true);
            return "login";
        }
        // 사용자 정보 세션에 저장
        session.setAttribute("user", user);
        // OTP 인증 페이지로 이동
        return "redirect:/otp-auth";
    }

    @GetMapping("/index")
    public String Index(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login"; // 로그인 페이지로 리디렉션
        }
        // 로그인한 사용자만 접근할 수 있는 자원에 대한 처리
        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "mypage";
    }


    @GetMapping("/otp-auth")
    public String otpForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 사용자가 로그인하지 않은 경우
            return "redirect:/login";
        }

        // 사용자가 OTP 등록되어 있는 경우
        if (user.getOtpSecret() != null && !user.getOtpSecret().matches("[0-9]{6}")) {
            model.addAttribute("otpEnabled", false);
            return "otp-auth";
        } else if (user.getOtpSecret() != null && user.getOtpSecret().matches("[0-9]{6}")) {
            model.addAttribute("otpEnabled", true);
            String qrCodeUrl = userService.generateQRUrl(user);
            model.addAttribute("qrCodeUrl", qrCodeUrl);
            return "otp-auth";
        }
        return "otp-auth";
    }

    @PostMapping("/otp-auth")
    public String otpAuth(@RequestParam String otpCode, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = false;
        try {
            int code = Integer.parseInt(otpCode);
            isCodeValid = gAuth.authorize(user.getOtpSecret(), code);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (isCodeValid) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("errorMessage", "OTP 번호가 일치하지 않습니다.");
            return "otp-auth";
        }
    }

    @GetMapping("/generate-code")
    @ResponseBody
    public String generateCode(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "FAIL";
        }

        int code = userService.generateCode(user);
        return Integer.toString(code);
    }


    // 비밀번호 변경 요청 처리
    @GetMapping("/ch_pwd")
    public String showChangePasswordPage(Model model, HttpSession session) {
        String userEmail = ((User) session.getAttribute("user")).getEmail();
        model.addAttribute("userEmail", userEmail);
        return "mypage";
    }

    @PostMapping("/ch_pwd")
    public String changePassword(HttpServletRequest request, HttpSession session, Model model) {
        String currentPassword = request.getParameter("cur_pwd");
        String newPassword = request.getParameter("new_pwd");
        String confirmPassword = request.getParameter("con_pwd");
        if (currentPassword == null || currentPassword.isEmpty()) {
            model.addAttribute("errorMessage", "현재 비밀번호를 입력해주세요.");
            return "mypage";
        }
        
        //HttpSession 객체를 선언하고, session.getAttribute("user.email")를 통해 현재 사용자의 이메일 값을 가져옴
        User currentUser = (User) session.getAttribute("user");
       // UserRepository에서 해당 사용자를 조회하고, 조회된 User 객체의 getPassword() 메소드를 사용하여 데이터베이스에 저장된 현재 사용자 비밀번호를 가져옵
        User user = userRepository.findByEmail((String) session.getAttribute("user.email"));
        //현재 비밀번호(current password)를 클라이언트에서 전달받아, 해당 비밀번호와 데이터베이스에 저장된 현재 사용자의 비밀번호를 비교
        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
            return "mypage";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            return "mypage";
        }

        boolean result = userService.changePassword(currentUser.getEmail(), currentPassword, newPassword);

        if (!result) {
            model.addAttribute("errorMessage", "비밀번호 변경에 실패했습니다.");
            return "mypage";
        }

        return "redirect:/mypage";
    }
}