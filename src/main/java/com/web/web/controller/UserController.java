package com.web.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.UserRequest;
import com.web.web.entity.User;
import com.web.web.security.JwtService;
import com.web.web.security.MyUserDetails;
import com.web.web.service.UserServiceInterface;
import com.web.web.service.impl.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @PostMapping("/signin")//ten và matKhau
    public ResponseEntity<?> login(@RequestBody UserRequest us) {
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            us.getUsername(),
                            us.getPassword()));

            // Nếu xác thực thành công, tạo token và trả về


            // Nếu xác thực thành công
            if (authentication.isAuthenticated()) {
                // Lấy thông tin Account từ đối tượng xác thực
                SecurityContextHolder.getContext().setAuthentication(authentication);
                MyUserDetails account = (MyUserDetails) authentication.getPrincipal();

                // Tạo JWT token
                String token = jwtService.generateToken(account.getUsername());
               
                // Trả về token và role
                return ResponseEntity.ok(
                        token
                        );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody UserRequest request){
        try{
            userService.register(request);
            return ResponseEntity.ok("please check your email to get confirm register code");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/confirmRegister")
    public ResponseEntity<?>confirmRegister(@RequestParam("username") String username,@RequestParam("registerCode") String registerCode){
        try{
            userService.confirmRegister(username,registerCode);
            return ResponseEntity.ok("register success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("username") String username){
        try{
            userService.resetPassword(username);
            return ResponseEntity.ok("please check your email to get confirm reset code");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/confirmResetPassword")
    public ResponseEntity<?> confirmResetPassword(@RequestParam("username") String username,@RequestParam("code") String code,
    @RequestParam("newPassword") String newPassword){
        try{
            userService.confirmReset(username,code,newPassword);
            return ResponseEntity.ok("reset success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest request){
        try{
            userService.updateYourProfile(request);
            return ResponseEntity.ok("update success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/yourProfile")
    public ResponseEntity<?> yourProfile(){
        try{
            return ResponseEntity.ok(userService.responseTofindCurrentUser());
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok(userService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Pageable pageable){
        try{
            return ResponseEntity.ok(userService.findAll(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }    
}
