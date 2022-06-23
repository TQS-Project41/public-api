package com.tqs.project.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.project.model.Business;
import com.tqs.project.model.BusinessCourierInteractions;
import com.tqs.project.model.Courier;
import com.tqs.project.model.User;
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

  @Test
  void givenNoLogin_whenApply_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/apply")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenLoginAsBusiness_whenApply_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/apply")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenCourierLogin_whenApplyToUnknownBusiness_returnStatusNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(1)).thenReturn(Optional.empty());
    when(courierService.getCourierById(0)).thenReturn(Optional.of(new Courier()));

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/apply")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());

  }

  @Test
  void givenCourierLogin_whenApplyFailed_returnStatusForbidden() {
    User user = new User();
    Business business = new Business();
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    when(businessService.getBusinessById(1)).thenReturn(Optional.of(business));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessCourierInteractionsService.apply(business, courier)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/apply")
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value());

  }

  @Test
  void givenCourierLogin_whenApply_returnStatusOK() {
    User user = new User();
    Business business = new Business();
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    when(businessService.getBusinessById(1)).thenReturn(Optional.of(business));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessCourierInteractionsService.apply(business, courier))
        .thenReturn(Optional.of(new BusinessCourierInteractions()));

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/apply")
        .then()
        .statusCode(HttpStatus.OK.value());

  }

  @Test
  void givenNoLogin_whenRefuse_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/refuse")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenLoginAsBusiness_whenRefuse_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/refuse")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenCourierLogin_whenRefuseToUnknownBusiness_returnStatusNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(1)).thenReturn(Optional.empty());
    when(courierService.getCourierById(0)).thenReturn(Optional.of(new Courier()));

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/refuse")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());

  }

  @Test
  void givenCourierLogin_whenRefuseFailed_returnStatusForbidden() {
    User user = new User();
    Business business = new Business();
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    when(businessService.getBusinessById(1)).thenReturn(Optional.of(business));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessCourierInteractionsService.refuse(business, courier)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/refuse")
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value());

  }

  @Test
  void givenCourierLogin_whenRefuse_returnStatusOK() {
    User user = new User();
    Business business = new Business();
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    when(businessService.getBusinessById(1)).thenReturn(Optional.of(business));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessCourierInteractionsService.refuse(business, courier))
        .thenReturn(Optional.of(new BusinessCourierInteractions()));

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/business/1/refuse")
        .then()
        .statusCode(HttpStatus.OK.value());

  }

  @Test
  void givenNoLogin_whenGettingActive_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business/active")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenLoginAsBusiness_whenGettingActive_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business/active")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenCourierLogin_whenGettingActive_returnStatusOk() {
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessCourierInteractionsService.getAllActive(courier)).thenReturn(Arrays.asList());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business/active")
        .then()
        .statusCode(HttpStatus.OK.value());

  }

  @Test
  void givenNoLogin_whenGettingAll_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenLoginAsBusiness_whenGettingAll_returnStatusUnauthorized() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.empty());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  void givenCourierLogin_whenGettingAll_returnStatusOk() {
    Courier courier = new Courier();

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(0)).thenReturn(Optional.of(courier));
    when(businessService.getAllBusinesss()).thenReturn(Arrays.asList());

    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/business/active")
        .then()
        .statusCode(HttpStatus.OK.value());

  }
  
}
