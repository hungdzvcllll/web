package com.web.web.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.AnswerRequest;
import com.web.web.service.impl.TestService;

@RestController
@RequestMapping("/Test")//bài kieemrtra,có thể tạo theo studySet
public class TestController {
    @Autowired
    TestService service;
    @GetMapping("/getTest")//tạo bài kiểm tra
    public ResponseEntity<?> getTest(@RequestParam("studySetId") Integer studySetId){
        try{
            return ResponseEntity.ok(service.createQuiz(studySetId));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("/submit")//nộp bài và chấm điểm,trả kết quả
    public ResponseEntity<?> submit(@RequestParam("studySetId") Integer studySetId,@RequestParam("name") String name,
    @RequestBody ArrayList<AnswerRequest>answers){
        try{
            return ResponseEntity.ok(service.submitTest(name, studySetId, answers));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")//tìm bài kiểm tra theo id
    public ResponseEntity<?> findById(@RequestParam("id") Integer id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findByStudySet")//tìm bài theo studySet
    public ResponseEntity<?> findByStudySet(@RequestParam("studySetId") Integer studySetId,Pageable pageable){
        try{
            return ResponseEntity.ok(service.findByStudySet(studySetId,pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
