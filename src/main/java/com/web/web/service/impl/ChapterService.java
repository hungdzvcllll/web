package com.web.web.service.impl;

import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.request.ChapterRequest;
import com.web.web.dto.response.ChapterResponse;
import com.web.web.entity.Chapter;
import com.web.web.entity.TextBook;
import com.web.web.mapper.ChapterMapper;
import com.web.web.mapper.TextBookMapper;
import com.web.web.repository.ChapterRepository;
import com.web.web.repository.TextBookRepository;

@Service
public class ChapterService {
    @Autowired
    FileService fileService;
    @Autowired
    ChapterRepository chapterRepo;
    @Autowired
    TextBookRepository textBookRepo;
    @Autowired
    ChapterMapper mapper;
    @Autowired
    UserService userService;
    public void saveChapter(ChapterRequest request)throws Exception{
        
        TextBook book=textBookRepo.findById(request.getTextBookId()).get();
        checkIfYouAreAuthor(request.getTextBookId());
        chapterRepo.save(new Chapter(null,request.getName(),fileService.saveFile(request.getSolve(),"textbook/"),book));
    }
    public void update(Integer id,ChapterRequest request)throws Exception{
        TextBook book=textBookRepo.findById(request.getTextBookId()).get();
        checkIfYouAreAuthor(request.getTextBookId());
        Chapter chap=chapterRepo.findById(id).get();
        Files.delete(fileService.getFullPathFromLink(chap.getSolveFile()));
        chap.setName(request.getName());
        chap.setSolveFile(fileService.saveFile(request.getSolve(),"textbook/"));
        chapterRepo.save(chap);
    }
    public void checkIfYouAreAuthor(Integer textBookId){
        Integer authorId=textBookRepo.findById(textBookId).get().getUser().getId();
        Integer currentUserId=userService.findCurrentUser().getId();
        if(authorId!=currentUserId)
            throw new RuntimeException("you don't have permission");
        
    }
    public ChapterResponse findById(Integer id){
        return mapper.toDTO(chapterRepo.findById(id).get());
    }
    public Page<ChapterResponse> findByTextBook(Integer textBookId,Pageable pageable){
        return chapterRepo.findByTextBook(textBookRepo.findById(textBookId).get(),pageable).map(mapper::toDTO);
    }
}
