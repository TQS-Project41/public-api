package com.tqs.project.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.project.model.User;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.service.UserService;

@RestController
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  @PostMapping("login")
  public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
    Optional<User> user = userService.getByEmailAndPassword(email, password);

    if (user.isPresent()) {
      Map<String, String> response = new HashMap<>();
      response.put("token", jwtUtils.generateJwtToken(user.get().getId()));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  
}
