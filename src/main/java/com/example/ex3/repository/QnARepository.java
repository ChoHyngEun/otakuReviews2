package com.example.ex3.repository;

import com.example.ex3.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnARepository extends JpaRepository<Question, Long> {
}