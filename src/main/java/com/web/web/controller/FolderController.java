package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.FolderService;

@RestController
@RequestMapping("/folder")
public class FolderController {
    @Autowired
    FolderService folderService;
    @PostMapping("/savePersonalFolder")
    public ResponseEntity<?> savePersonalFolder(@RequestParam("name")String name,@RequestParam("isPrivate")Boolean isPrivate){
        try{
            folderService.savePersonalFolder(name,isPrivate);
            return ResponseEntity.ok("save success");
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("/saveClassFolder")
    public ResponseEntity<?> saveClassFolder(@RequestParam("name")String name, //chưa test
    @RequestParam("classId")Integer classId){
        try{
            folderService.saveClassFolder(name, classId);
            return ResponseEntity.ok("save success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("name")String name, //chưa test
    @RequestParam("isPrivate")Boolean isPrivate,@RequestParam("id")Integer id){
        try{
            folderService.updateFolder(id, name, isPrivate);
            return ResponseEntity.ok("update success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("findClassFolder")
    public ResponseEntity<?> findClassFolder(@RequestParam("classId")Integer classId,Pageable pageable){
        try{
            return ResponseEntity.ok(folderService.findClassFolder(classId,pageable));
            
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        try{
            return ResponseEntity.ok(folderService.findById(id));
            
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam("name")String name,Pageable pageable){
        try{
            return ResponseEntity.ok(folderService.findPublicByName(name,pageable));    
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
    
}
