package com.tqs.project.controller;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.project.service.UserService;
import com.tqs.project.service.DeliveryService;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.User;
import com.tqs.project.security.AuthTokenFilter;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.CourierService;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = CourierController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class CourierControllerMockMvcTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private CourierService courierService;

  @MockBean
  private UserService userService;

  @MockBean
  private DeliveryService deliveryService;

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
  

  @Test
  void whenGetCouriersAndNotRegister_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/couriers")
        .then()
        .statusCode(404);

    verify(courierService, VerificationModeFactory.times(0)).getAllCouriers();

  }
  
  @Test
  void whenGetCouriers_thenReturnsAllCouriers() {
    Courier a = new Courier();
    Courier b = new Courier();
    List<Courier> lst = new ArrayList<>();
    lst.add(a);
    lst.add(b);

    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getAllCouriers()).thenReturn(lst);


    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/couriers")
        .then()
        .statusCode(200).and().
        body("size", equalTo(2));

    verify(courierService, VerificationModeFactory.times(1)).getAllCouriers();

  }

  @Test
  void whenGetCourierByIdAndNoLogin_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/couriers/{id}",1)
        .then()
        .statusCode(404);

    verify(courierService, VerificationModeFactory.times(0)).getAllCouriers();

  }

  @Test
  void whenGetCourierByIdAndIdNotRegister_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(2L)).thenReturn(Optional.empty());
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/couriers/{id}",1)
        .then()
        .statusCode(404);

    verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser();

  }


  @Test
  void whenGetCourierById_thenReturnCourier() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(1L)).thenReturn(Optional.of(new Courier()));
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .get("/couriers/{id}",1)
        .then()
        .statusCode(200);

    verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser();

  }

  @Test
  void whenPutCourierByIdAndNoLogin_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .put("/couriers/{id}",1)
        .then()
        .statusCode(404);

    verify(courierService, VerificationModeFactory.times(0)).getAllCouriers();

  }

  @Test
  void whenPutCourierByIdAndIdNotRegister_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(2L)).thenReturn(Optional.empty());
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .put("/couriers/{id}",1)
        .then()
        .statusCode(404);

    verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser();

  }
  @Test
  void whenPutCourierById_thenReturnUpdatedCourier() {
    Courier b = new Courier();
    b.setId(1L);
    b.setName("alex");
    b.setUser(new User("alex20002011@gmail.com", "aaa"));
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(courierService.getCourierById(1L)).thenReturn(Optional.of(b));
    when(courierService.save(any())).thenReturn(b);
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .put("/couriers/{id}?name=alex&password=bbbbb&photo=xxxxx",1)
        .then()
        .statusCode(200).and().
        body("name", equalTo("alex"));

    verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser();
    verify(courierService, VerificationModeFactory.times(1)).getCourierById(1);

  }


  @Test
  void whenPostListen_thenReturnNotFound() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

      
    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/couriers/listen/")
        .then()
        .statusCode(200).and().body("size", equalTo(0));

    verify(deliveryService, VerificationModeFactory.times(0)).deliveriesWithoutCourier(any() );

  }

  @Test
  void whenPostListen_thenGetDeliveries() {
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    Delivery d1 = new Delivery();
    d1.setStatus(DeliveryStatusEnum.QUEUED);

    Delivery d2 = new Delivery();
    d2.setStatus(DeliveryStatusEnum.QUEUED);

    Pageable pageable = Pageable.unpaged();
    Page<Delivery> ret= new PageImpl<>(Arrays.asList(d1, d2), pageable, 2);
    when(deliveryService.deliveriesWithoutCourier(any())).thenReturn(ret);


    RestAssuredMockMvc.given()
        .contentType("application/json")
        .when()
        .post("/couriers/listen/")
        .then()
        .statusCode(200).and().body("size", equalTo(2));

    verify(deliveryService, VerificationModeFactory.times(1)).deliveriesWithoutCourier(any() );

  }


} 
