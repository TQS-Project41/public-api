package com.tqs.project.controller;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

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

import com.tqs.project.model.Courier;
import com.tqs.project.security.AuthTokenFilter;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.CourierService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = CourierController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class CourierControllerMockMvcTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private CourierService courierService;

  @MockBean
  private JwtUtils jwtUtils;

  @MockBean
  private AuthTokenFilter authTokenFilter;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
  void whenRegistering_thenReturnsHttpCreated() {
    when(courierService.save(any(Courier.class))).thenReturn(new Courier());

    Map<String, String> body = new HashMap<>();
    body.put("email", "pedro.dld@ua.pt");
    body.put("name", "Pedro");
    body.put("password", "password");
    body.put("birthdate", "10-02-2001");
    body.put("photo", "10-02-2001");

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/couriers")
        .then()
        .statusCode(201);

    verify(courierService, VerificationModeFactory.times(1)).save(any(Courier.class));

  }
  
}
