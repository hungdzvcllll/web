package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.UserInClassService;

@RestController
@RequestMapping("/userInClass")
public class UserInClassController {
    @Autowired
    UserInClassService service;
    @GetMapping("/yourClass")
    public  ResponseEntity<?> yourClass(Pageable pageable){
        try{
            
            return ResponseEntity.ok(service.yourClass(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteStudent")
    public ResponseEntity<?> deleteStudent(@RequestParam("userId")Integer userId,@RequestParam("classId")Integer classId){
        try{
            service.deleteStudentInClass(userId, classId);
            return ResponseEntity.ok("delete success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestParam("userId")Integer userId,@RequestParam("classId")Integer classId){
        try{
            service.addStudentInClass(userId, classId);
            return ResponseEntity.ok("delete success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/userInClass")
    public ResponseEntity<?> userInClass(@RequestParam("classId")Integer classId,Pageable pageable){
        try{
           
            return ResponseEntity.ok( service.userInClass(classId,pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
