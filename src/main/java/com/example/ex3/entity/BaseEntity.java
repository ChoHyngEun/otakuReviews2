package com.example.ex3.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    
    //게시글 작성일자 (수정x)
    @CreatedDate
    @Column(name="regdate",updatable=false)
    private LocalDateTime regDate;

    //게시글 수정일자
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}
