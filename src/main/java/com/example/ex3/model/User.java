package com.example.ex3.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @Column(nullable = true, unique = true)
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Column(nullable = false)
    private String otpSecret;

    public User() {
        this.otpSecret = generateRandomOtpSecret();
    }

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.otpSecret = generateRandomOtpSecret();
    }

    private String generateRandomOtpSecret() {
        // 6자리 랜덤 숫자 생성
        return String.format("%06d", (int) (Math.random() * 999999));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpSecret() {
        return otpSecret;
    }

    public void setOtpSecret(String otpSecret) {
        this.otpSecret = otpSecret;
    }

    public void updateOtpSecret() {
        // 6자리 랜덤 숫자 생성
        String newOtpSecret = String.format("%06d", (int) (Math.random() * 999999));
        this.setOtpSecret(newOtpSecret);
    }
}