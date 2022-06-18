package com.tqs.project.service;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tqs.project.model.Address;
import com.tqs.project.model.Business;
import com.tqs.project.model.Shop;

import com.tqs.project.repository.ShopRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class NominatimServiceServiceTest {

    @InjectMocks
    NominatimService nominatimService;
    @Test
     void whenTestRealAddress_ThenReturnLatitudeAndLongitude() throws IOException, InterruptedException, ParseException {
        Map<String, Double> map = nominatimService.getAddress("Rua Mário Sacramento", "Aveiro", "4550-103", "Portugal");
        
        assertThat(map.size()).isEqualTo(2);
        assertTrue(map.containsKey("lat"));
        assertTrue(map.containsKey("lon"));
    }

    @Test
     void whenTestInvalidAddress_MapEmpty() throws IOException, InterruptedException, ParseException {
        Map<String, Double> map = nominatimService.getAddress("Rua Márioasadssdasadsdasdasad Sacramento", "Aveiro", "4550-103", "Portugal");
        
        assertThat(map.size()).isEqualTo(0);
        assertFalse(map.containsKey("lat"));
        assertFalse(map.containsKey("lon"));
    }
    

    
}
