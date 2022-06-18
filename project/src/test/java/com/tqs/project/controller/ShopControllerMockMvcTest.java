package com.tqs.project.controller;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
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

import com.tqs.project.model.Address;
import com.tqs.project.model.Business;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Shop;
import com.tqs.project.model.User;
import com.tqs.project.security.AuthTokenFilter;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.BusinessService;
import com.tqs.project.service.CourierService;
import com.tqs.project.service.NominatimService;
import com.tqs.project.service.ShopService;
import com.tqs.project.service.UserService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = ShopController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class ShopControllerMockMvcTest {
    @Autowired
  private MockMvc mvc;
  @MockBean
  private CourierService courierService;
  @MockBean
    private ShopService service;

    @MockBean
    private UserService userService;

    @MockBean
    private BusinessService businessService;
    @MockBean
    private NominatimService nominatimService;

  @MockBean
  private JwtUtils jwtUtils;

  @MockBean
  private AuthTokenFilter authTokenFilter;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
  void whenRegistering_thenReturnsHttpCreated() throws IOException, InterruptedException, ParseException {

    when(service.save(any(Shop.class))).thenReturn(new Shop());
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(anyLong())).thenReturn(Optional.of(new Business()));
    Map<String, Double> value= new HashMap<>();
    value.put("lat", 5.0);
    value.put("lon", 15.0);
    Map<String, String> args= new HashMap<>();
    args.put("country", "Portugal");
    args.put("city", "Aveiro");
    args.put("zipcode", "4550-103");
    args.put("country", "Rua Mário Sacramento");

    when(nominatimService.getAddress(any(),any(),any(),any())).thenReturn(value);
    Map<String, Object> body = new HashMap<>();
    body.put("name", "Puma");
    body.put("address", args);

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/shop")
        .then()
        .statusCode(201);

    
    verify(service, VerificationModeFactory.times(1)).save(any(Shop.class));

  }

  @Test
  void whenRegistering_thenGiveBadURL() throws IOException, InterruptedException, ParseException {

    when(service.save(any(Shop.class))).thenReturn(new Shop());
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(anyLong())).thenReturn(Optional.of(new Business()));
    
    Map<String, String> args= new HashMap<>();
    args.put("country", "Portugal");
    args.put("city", "Aveiro");
    args.put("zipcode", "4550-103");
    args.put("country", "Rua Mário Sacramento");

    when(nominatimService.getAddress(any(),any(),any(),any())).thenReturn(null);
    Map<String, Object> body = new HashMap<>();
    body.put("name", "Puma");
    body.put("address", args);

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/shop")
        .then()
        .statusCode(400);

    
    verify(service, VerificationModeFactory.times(0)).save(any(Shop.class));

  }

  @Test
  void InvalidUser_ReturnForbidden() throws IOException, InterruptedException, ParseException {

    when(service.save(any(Shop.class))).thenReturn(new Shop());
    when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    when(businessService.getBusinessById(anyInt())).thenReturn(Optional.of(new Business()));
    Map<String, Double> value= new HashMap<>();
    value.put("lat", 5.0);
    value.put("lon", 15.0);
    Map<String, String> args= new HashMap<>();
    args.put("country", "Portugal");
    args.put("city", "Aveiro");
    args.put("zipcode", "4550-103");
    args.put("country", "Rua Mário Sacramento");

    when(nominatimService.getAddress(any(),any(),any(),any())).thenReturn(value);
    Map<String, Object> body = new HashMap<>();
    body.put("name", "Puma");

    body.put("address", args);

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/shop")
        .then()
        .statusCode(403);

    verify(service, VerificationModeFactory.times(0)).save(any(Shop.class));

  }

  @Test
  void InvalidUserBusiness_ReturnForbidden() throws IOException, InterruptedException, ParseException {

    when(service.save(any(Shop.class))).thenReturn(new Shop());
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(anyInt())).thenReturn(Optional.empty());
    Map<String, Double> value= new HashMap<>();
    value.put("lat", 5.0);
    value.put("lon", 15.0);
    Map<String, String> args= new HashMap<>();
    args.put("country", "Portugal");
    args.put("city", "Aveiro");
    args.put("zipcode", "4550-103");
    args.put("country", "Rua Mário Sacramento");

    when(nominatimService.getAddress(any(),any(),any(),any())).thenReturn(value);
    Map<String, Object> body = new HashMap<>();
    body.put("name", "Puma");

    body.put("address", args);

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/shop")
        .then()
        .statusCode(403);


    verify(service, VerificationModeFactory.times(0)).save(any(Shop.class));

  }

  @Test
  void InvalidArgs_ReturnBadRequest() throws IOException, InterruptedException, ParseException {

    when(service.save(any(Shop.class))).thenReturn(new Shop());
    when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
    when(businessService.getBusinessById(anyInt())).thenReturn(Optional.empty());
    Map<String, Double> value= new HashMap<>();
    value.put("lat", 5.0);
    value.put("lon", 15.0);
    Map<String, String> args= new HashMap<>();
    args.put("country", "Portugal");
    args.put("city", "Aveiro");
    args.put("zipcode", "4550-103");
    args.put("country", "Rua Mário Sacramento");

    when(nominatimService.getAddress(any(),any(),any(),any())).thenReturn(value);
    Map<String, Object> body = new HashMap<>();

    body.put("address", args);

    RestAssuredMockMvc.given().body(body)
        .contentType("application/json")
        .when()
        .post("/shop")
        .then()
        .statusCode(400);


    verify(service, VerificationModeFactory.times(0)).save(any(Shop.class));

  }

}
