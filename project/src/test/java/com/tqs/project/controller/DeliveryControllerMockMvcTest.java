package com.tqs.project.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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


  @Test
    void whenGetFee_thenReturnFee() {
        when(deliveryService.getFee()).thenReturn(5.0);
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/fee")
            .then()
            .statusCode(200).and().body("fee", equalTo(5.0f));
  }

  @Test
    void whenCreateDeliveryWithBadArgs_ThenReturnBadArgs() {

        Map<String,Object> body = new HashMap<>();
        body.put("deliveryTimestamp", "10-05-2022 10:30");
        body.put("shopId", "1L");

        RestAssuredMockMvc.given().body(body)
            .contentType("application/json")
            .when()
            .post("/delivery")
            .then()
            .statusCode(400);
  }

  @Test
    void whenCreateDeliveryWithoutValidShop_ThenReturnNotFound() {

        Map<String,Object> body = new HashMap<>();
        Map<String,String> address_values = new HashMap<>();
        address_values.put("country", "Portugal");
        address_values.put("zipcode", "6120-443");
        address_values.put("city", "Portugal");
        address_values.put("address", "Rua da estia");

        body.put("deliveryTimestamp", "10-05-2022 10:30");
        body.put("shopId", 1L);
        body.put("clientName", "Alexandre Serras");
        body.put("clientPhoneNumber", "964546324");
        body.put("address", address_values);


        RestAssuredMockMvc.given().body(body)
            .contentType("application/json")
            .when()
            .post("/delivery")
            .then()
            .statusCode(404);
  }


  @Test
    void whenCreateDeliveryValidShop_ThenReturnNotFound() throws IOException, InterruptedException, ParseException {



        Map<String,Object> body = new HashMap<>();
        Map<String,String> address_values = new HashMap<>();
        address_values.put("country", "Portugal");
        address_values.put("zipcode", "6120-443");
        address_values.put("city", "Portugal");
        address_values.put("address", "Rua da estia");

        body.put("deliveryTimestamp", "10-05-2022 10:30");
        body.put("shopId", 1L);
        body.put("clientName", "Alexandre Serras");
        body.put("clientPhoneNumber", "964546324");
        body.put("address", address_values);


        when(shopService.getShopById(1L)).thenReturn(Optional.of(new Shop()));
        when(nominatiumService.getAddress("Rua da estia","Portugal","6120-443","Portugal")).thenReturn(new HashMap<>());


        RestAssuredMockMvc.given().body(body)
            .contentType("application/json")
            .when()
            .post("/delivery")
            .then()
            .statusCode(404);
  }


  @Test
    void whenCreateDeliveryValidShop_ThenReturnDelivery() throws IOException, InterruptedException, ParseException {
        Map<String,Object> body = new HashMap<>();
        Map<String,String> address_values = new HashMap<>();
        address_values.put("country", "Portugal");
        address_values.put("zipcode", "6120-443");
        address_values.put("city", "Portugal");
        address_values.put("address", "Rua da estia");

        body.put("deliveryTimestamp", "10-05-2022 10:30");
        body.put("shopId", 1L);
        body.put("clientName", "Alexandre Serras");
        body.put("clientPhoneNumber", "964546324");
        body.put("address", address_values);

        Map<String,Double> map=new HashMap<>();
        map.put("lat", 5.0);
        map.put("lon", -5.0);

        when(shopService.getShopById(1L)).thenReturn(Optional.of(new Shop()));
        when(nominatiumService.getAddress("Rua da estia","Portugal","6120-443","Portugal")).thenReturn(map);
        when(deliveryService.save(any())).thenReturn(new Delivery());

        RestAssuredMockMvc.given().body(body)
            .contentType("application/json")
            .when()
            .post("/delivery")
            .then()
            .statusCode(201);
  }



  @Test
    void whenGetDeliveryByInvalidIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .get("/delivery/{id}",1)
            .then()
            .statusCode(404);
  }

  
  @Test
  void whenGetDeliveryByIdDelivery_ThenReturnDelivery() throws IOException, InterruptedException, ParseException {
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .get("/delivery/{id}",1)
          .then()
          .statusCode(200);
    }
   

  @Test
    void whenDeleteDeliveryByInvalidIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .delete("/delivery/{id}",1)
            .then()
            .statusCode(404);
  }

  @Test
  void whenDeleteDeliveryByIdDelivery_ThenReturnDelivery() throws IOException, InterruptedException, ParseException {
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .delete("/delivery/{id}",1)
          .then()
          .statusCode(200);
    }
    @Test
    void whenDeleteDeliveryByIdDeliveryWithInvalidState_ThenReturnForbidden() throws IOException, InterruptedException, ParseException {
        Delivery d = new Delivery();
        d.setStatus(DeliveryStatusEnum.COLLECTING);
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(d));
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .delete("/delivery/{id}",1)
            .then()
            .statusCode(403);
    }

    @Test
    void whenPutInvalidDeliveryByIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/accept",1)
            .then()
            .statusCode(404);
  }


  @Test
    void whenPutDeliveryByInvalidIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/accept",1)
            .then()
            .statusCode(404);
  }
  @Test
  void whenPutDeliveryByInvalidCourier_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.empty());

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/accept",1)
          .then()
          .statusCode(404);
    }  

    @Test
    void whenPutDeliveryByCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(new Courier()));

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/accept",1)
          .then()
          .statusCode(200);
    }
    @Test
    void whenPutDeliveryNotOnQueudByCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
        Delivery d = new Delivery();
        d.setStatus(DeliveryStatusEnum.COLLECTING);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(d));
        when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(new Courier()));

        RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/accept",1)
          .then()
          .statusCode(403);
    }


    @Test
    void whenPutInvalidCollectDeliveryByIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/collect",1)
            .then()
            .statusCode(404);
    }


  @Test
    void whenPutDeliveryByInvalidCollectIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/collect",1)
            .then()
            .statusCode(404);
  }
  @Test
  void whenPutDeliveryByInvalidAtributedCourier_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.empty());

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/collect",1)
          .then()
          .statusCode(404);
    }  

    @Test
    void whenPutDeliveryByInvalidCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(new Courier()));

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/collect",1)
          .then()
          .statusCode(403);
    }
    @Test
    void whenPutDeliveryOnCollectByCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
        Delivery d = new Delivery();
        d.setStatus(DeliveryStatusEnum.COLLECTING);
        Courier c =new Courier();
        c.setId(1l);
        d.setCourier(c);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(d));
        when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(c));

        RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/collect",1)
          .then()
          .statusCode(200);
    }


    @Test
    void whenPutInvalidDeliverDeliveryByIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/deliver",1)
            .then()
            .statusCode(404);
    }


  @Test
    void whenPutDeliveryByDeliverIdDelivery_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
            .contentType("application/json")
            .when()
            .put("/delivery/{id}/deliver",1)
            .then()
            .statusCode(404);
  }
  @Test
  void whenPutDeliveryByInvalidAtributedvCourier_ThenReturnNotFoundDelivery() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.empty());

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/deliver",1)
          .then()
          .statusCode(404);
    }  

    @Test
    void whenPutDeliveryByInvalidvCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
  
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
      when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(new Delivery()));
      when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(new Courier()));

      RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/deliver",1)
          .then()
          .statusCode(403);
    }
    @Test
    void whenPutDeliveryOnDeliverByCourier_ThenChangeDeliveryState() throws IOException, InterruptedException, ParseException {
        Delivery d = new Delivery();
        d.setStatus(DeliveryStatusEnum.DELIVERING);
        Courier c =new Courier();
        c.setId(1l);
        d.setCourier(c);

        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(new User()));
        when(deliveryService.getDeliveryById(anyLong())).thenReturn(Optional.of(d));
        when(courierService.getCourierById(anyLong())).thenReturn(Optional.of(c));

        RestAssuredMockMvc.given()
          .contentType("application/json")
          .when()
          .put("/delivery/{id}/deliver",1)
          .then()
          .statusCode(200);
    }
}
