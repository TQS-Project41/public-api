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
import com.tqs.project.service.NominatimService;
import com.tqs.project.service.ShopService;
import com.tqs.project.service.BusinessService;
import com.tqs.project.model.Business;
import com.tqs.project.model.Courier;
import com.tqs.project.model.Delivery;
import com.tqs.project.model.DeliveryStatusEnum;
import com.tqs.project.model.Shop;
import com.tqs.project.model.User;
import com.tqs.project.security.AuthTokenFilter;
import com.tqs.project.security.JwtUtils;
import com.tqs.project.security.WebSecurityConfig;
import com.tqs.project.service.CourierService;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = DeliveryController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class DeliveryControllerMockMvcTest {
    
    @Autowired
    private MockMvc mvc;
  
    @MockBean
    private CourierService courierService;
  
    @MockBean
    private UserService userService;
  
    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private ShopService shopService;
  
    @MockBean
    private BusinessService busService;

    @MockBean
    private NominatimService nominatiumService;

    @MockBean
    private JwtUtils jwtUtils;
  
    @MockBean
    private AuthTokenFilter authTokenFilter;
  
    @BeforeEach
    void setUp() {
      RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostListen_thenReturnNotFound() {
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/")
            .then()
            .statusCode(200).and().body("size", equalTo(0));

        verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser( );
  }

  @Test
    void whenGetShopAndCourierEmpty_thenReturnNotFound() {
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(courierService.getAllCouriers()).thenReturn(new ArrayList<>());
        when(shopService.getAllShops()).thenReturn(new ArrayList<>());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/")
            .then()
            .statusCode(200).and().body("size", equalTo(0));

        verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser( );
  }

  @Test
    void whenGetShopAndEmptyCourier_thenReturnNotFound() {
        User u = new  User();
        u.setId(1l);
        Business b = new Business();
        b.setUser(u);

        Delivery d1 = new Delivery();
        d1.setStatus(DeliveryStatusEnum.QUEUED);

        Delivery d2 = new Delivery();
        d2.setStatus(DeliveryStatusEnum.QUEUED);

        Pageable pageable = Pageable.unpaged();
        Page<Delivery> ret= new PageImpl<>(Arrays.asList(d1, d2), pageable, 2);

        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(u));
        when(courierService.getCourierById(1l)).thenReturn(Optional.empty());
        when(shopService.getShopByBusinessId(1L)).thenReturn(Optional.of(b));
        when(deliveryService.getAll(eq(null),eq(1L),any(Pageable.class))).thenReturn(ret);

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/")
            .then()
            .statusCode(200).and().body("size", equalTo(2));


        verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser( );
  }

  @Test
    void whenGetShopEmptyAndCourier_thenReturnNotFound() {

        User u = new  User();
        u.setId(1l);

        Courier c = new Courier();
        c.setUser(u);
        Delivery d1 = new Delivery();
        d1.setStatus(DeliveryStatusEnum.QUEUED);

        Delivery d2 = new Delivery();
        d2.setStatus(DeliveryStatusEnum.QUEUED);

        Pageable pageable = Pageable.unpaged();
        Page<Delivery> ret= new PageImpl<>(Arrays.asList(d1, d2), pageable, 2);

        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(u));
        when(courierService.getCourierById(1L)).thenReturn(Optional.of(c));
        when(shopService.getShopByBusinessId(anyInt())).thenReturn(Optional.empty());
        when(deliveryService.getAll(eq(1L),eq(null),any(Pageable.class))).thenReturn(ret);

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/")
            .then()
            .statusCode(200).and().body("size", equalTo(2));


        verify(userService, VerificationModeFactory.times(1)).getAuthenticatedUser( );
  }


 
}
