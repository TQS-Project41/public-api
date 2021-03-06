package com.tqs.project.service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.repository.BusinessRepository;
import com.tqs.project.model.Business;
import com.tqs.project.model.Shop;
import com.tqs.project.repository.ShopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired(required = true)
    private ShopRepository rep;

    @Autowired(required = true)
    private BusinessRepository repBusiness;


    public Shop save(Shop shop) {
        return rep.save(shop);
    }
    
    public List<Shop> getAllShops() {
        return rep.findAll();
    }

    public Optional<Business> getShopByBusinessId(long id) {
        return repBusiness.findById(id);
    }

    public Optional<Shop> getShopById(long id) {
        return rep.findById(id);
    }
}