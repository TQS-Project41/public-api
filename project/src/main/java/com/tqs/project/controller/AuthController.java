package com.tqs.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Optional;
import com.tqs.project.Model.User;
import com.tqs.project.Service.UserService;
import com.tqs.project.security.JwtUtils;

@Controller
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  @PostMapping("login")
  public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
  
}
