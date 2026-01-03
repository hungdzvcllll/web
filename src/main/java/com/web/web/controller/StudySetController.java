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

import com.web.web.service.impl.StudySetService;

@RestController
@RequestMapping("/studySet")
//studySet chứa StudySetItem(các cặp khái niệm và định nghĩa)
public class StudySetController {
    @Autowired
    StudySetService service;
    @PostMapping("/create")//tạo
    public ResponseEntity<?>create(@RequestParam("folderId")Integer folderId,@RequestParam("name")String name){
        try{
            service.create(folderId, name);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")//tìm bằng id
    public ResponseEntity<?>findById(@RequestParam("id")Integer id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findByFolderId")//tìm theo folderId
    public ResponseEntity<?>findByFolderId(@RequestParam("folderId")Integer folderId,Pageable pageable){
        try{
            return ResponseEntity.ok(service.findByFolder(folderId, pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
