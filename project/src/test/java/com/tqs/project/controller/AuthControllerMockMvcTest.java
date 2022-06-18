package com.tqs.project.controller;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.project.model.User;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.UserService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = AuthController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerMockMvcTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtUtils jwtUtils;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
  void givenUserExists_whenLoggingIn_thenReturnsToken() {
    when(userService.getByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.of(new User()));
    when(jwtUtils.generateJwtToken(any(long.class))).thenReturn("");

    Map<String, String> body = new HashMap<>();
    body.put("email", "pedro.dld@ua.pt");
    body.put("password", "password");

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/login")
        .then()
        .statusCode(200)
        .body("token", equalTo(""));

    verify(userService, VerificationModeFactory.times(1)).getByEmailAndPassword(any(String.class), any(String.class));
    verify(jwtUtils, VerificationModeFactory.times(1)).generateJwtToken(any(long.class));
  }

  @Test
  void givenUserDoesNotExist_whenLoggingIn_thenReturnsHttpNotFound() {
    when(userService.getByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());

    Map<String, String> body = new HashMap<>();
    body.put("email", "pedro.dld@ua.pt");
    body.put("password", "password");

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/login")
        .then()
        .statusCode(404);

    verify(userService, VerificationModeFactory.times(1)).getByEmailAndPassword(any(String.class), any(String.class));

  }

  
}
