package com.tqs.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.Address;
import com.tqs.project.Model.Business;
import com.tqs.project.Model.Delivery;
import com.tqs.project.Model.Shop;

import com.tqs.project.Repository.ShopRepository;

import com.tqs.project.Service.ShopService;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
 class ShopServiceTest {

    @Mock( lenient = true)
    private ShopRepository rep;

    @InjectMocks
    private ShopService service;

    @BeforeEach
    public void setUp() {

        Shop coviran = new Shop("Coviran", new Address(), new Business(), new HashSet<Delivery>());
        coviran.setId(111L);

        Shop pingodoce = new Shop("Pingo Doce", new Address(), new Business(), new HashSet<Delivery>());

        List<Shop> allShops = Arrays.asList(coviran, pingodoce);

        Mockito.when(rep.findById(coviran.getId())).thenReturn(Optional.of(coviran));
        Mockito.when(rep.findAll()).thenReturn(allShops);
        Mockito.when(rep.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenSearchShopId_thenShopShouldBeFound() {
        Optional<Shop> found = service.getShopById( 111L );
        Shop shop = null;
        if (found.isPresent()) shop = found.get();

        assertThat(shop.getId()).isEqualTo(111L);

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
     void whenSearchInvalidId_thenShopShouldNotBeFound() {
        Optional<Shop> found = service.getShopById(-99L);
        Shop shop = null;
        if (found.isPresent()) shop = found.get();

        // verify if FindById is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());

        assertThat(shop).isNull();
    }


    @Test
     void whengetAll_thenReturn2Records() {
        Shop coviran = new Shop("Coviran", new Address(), new Business(), new HashSet<Delivery>());
        Shop pingodoce = new Shop("Pingo Doce", new Address(), new Business(), new HashSet<Delivery>());

        List<Shop> allShops = service.getAllShops();

        // verify if FindAllShops is called once
        Mockito.verify(rep, VerificationModeFactory.times(1)).findAll();

        assertThat(allShops).hasSize(2).extracting(Shop::getName).contains(coviran.getName(), pingodoce.getName());
    }
 }