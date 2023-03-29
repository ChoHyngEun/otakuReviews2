//서비스 계층에서는 목록 처리..
/*
* PageRequestDTO를 파라미터로, PageResultDTO를 리턴 타입으로 사용하는 getList() 설계
* 앤티티 객체를 DTO 객체로 변환하는 entityToDto()를 정의
*
* */

package com.example.ex3.service;

import com.example.ex3.dto.GuestbookDTO;
import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.dto.PageResultDTO;
import com.example.ex3.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    GuestbookDTO read(Long gno);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    void remove(Long gno);
    void modify(GuestbookDTO dto);
}
