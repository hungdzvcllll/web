package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.TextBookResponse;
import com.web.web.entity.TextBook;
import com.web.web.mapper.TextBookMapper;
import com.web.web.repository.TextBookRepository;

@Service
public class TextBookService {
    @Autowired
    UserService userService;
    @Autowired
    TextBookRepository textBookRepo;
    @Autowired
    TextBookMapper mapper;
    public void add(String name){
        
        TextBook book=new TextBook(null,name,userService.findCurrentUser(),null);
        textBookRepo.save(book);
    }
    public TextBookResponse findById(Integer id){
        TextBook book=textBookRepo.findById(id).get();
        return new TextBookResponse(book.getId(),book.getName(),book.getUser().getId(),book.getUser().getUsername());
    }
    public Page<TextBookResponse> findAll(Pageable pageable){
        return textBookRepo.findAll(pageable).map(mapper::toDTO);
    }
    
}
