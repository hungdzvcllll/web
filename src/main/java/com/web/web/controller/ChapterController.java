package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.ChapterRequest;
import com.web.web.service.impl.ChapterService;
@RestController
@RequestMapping("/Chapter")
//Chapter là chương trong sách
public class ChapterController {
    @Autowired
    ChapterService service;
    //thêm chương mới
    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute ChapterRequest request){
        try{
            service.saveChapter(request);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }  
    }
    //update 1 chương
    @PutMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute ChapterRequest request,@RequestParam("id")Integer id){
        try{
            service.update(id, request);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }  
    }
    //tìm chương theo id
    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
          catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }  
    }
    //tìm theo sách
     @GetMapping("/findByTextBook")
    public ResponseEntity<?> findAll(Pageable pageable,@RequestParam("textBookId")Integer textBookId){
        try{
            return ResponseEntity.ok(service.findByTextBook(textBookId,pageable));
        }
          catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }  
    }
}
