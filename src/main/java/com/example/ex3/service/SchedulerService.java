package com.example.ex3.service;

import com.example.ex3.model.User;
import com.example.ex3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class SchedulerService {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void updateOtpSecret() {
        List<User> users = userRepository.findAll(); // 전체 사용자 조회
        for (User user : users) {
            user.updateOtpSecret(); // OtpSecret 필드 값 변경
            userRepository.save(user); // 변경된 값 저장
        }
    }
}
