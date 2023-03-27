package com.example.ex3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex3.model.User;
import com.example.ex3.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateOtpSecret(User user) {
        Random rand = new Random();
        int otp = rand.nextInt(900000) + 100000; // 100000~999999 사이의 랜덤 숫자 생성
        user.setOtpSecret(Integer.toString(otp));
        userRepository.save(user);
    }
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public boolean verifyCode(User user, int code) {
        return gAuth.authorize(user.getOtpSecret(), code);
    }

    public String generateQRUrl(User user) {
        return gAuth.qrCodeGoogleUrl("Ex3", user.getName(), user.getOtpSecret());
    }

    public int generateCode(User user) {
        return gAuth.getTotpPassword(user.getOtpSecret());
    }
}