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

import com.tqs.project.model.Business;
import com.tqs.project.security.AuthTokenFilter;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.BusinessCourierInteractionsService;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.UserService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = BusinessController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class BusinessControllerMockMvcTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private BusinessService businessService;

  @MockBean
  private UserService userService;

  @MockBean
  private CourierService courierService;

  @MockBean
  private BusinessCourierInteractionsService businessCourierInteractionsService;

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
    when(businessService.save(any(Business.class))).thenReturn(new Business());

    Map<String, String> body = new HashMap<>();
    body.put("email", "pedro.dld@ua.pt");
    body.put("password", "password");

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/business")
        .then()
        .statusCode(201);

    verify(businessService, VerificationModeFactory.times(1)).save(any(Business.class));

  }
  
}
