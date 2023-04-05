package com.example.ex3.service;

import com.example.ex3.DTO.GuestbookDTO;
import com.example.ex3.DTO.PageRequestDTO;
import com.example.ex3.DTO.PageResultDTO;
import com.example.ex3.entity.Guestbook;

import java.time.LocalDateTime;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

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

    GuestbookDTO read (Long gno);

    void remove (Long gno);
    void modify(GuestbookDTO dto);
}

