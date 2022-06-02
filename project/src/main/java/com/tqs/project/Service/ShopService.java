package com.tqs.project.Service;

import java.util.List;
import java.util.Optional;

import com.tqs.project.Model.Shop;
import com.tqs.project.Repository.ShopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired(required = true)
    private ShopRepository rep;


    public Shop save(Shop shop) {
        return rep.save(shop);
    }

    public List<Shop> saveAll(List<Shop> shops) {
        return rep.saveAll(shops);
    }
    
    public List<Shop> getAllShops() {
        return rep.findAll();
    }

    public Optional<Shop> getShopById(long id) {
        return rep.findById(id);
    }

    /* *************** Talvez precisaremos mais tarde ***************
    public Shop getShopByAddress(Adress address) {
        return rep.findByAddress(address);
    } 
    */

    public Shop update(Shop shop) {
        Shop existingShop = rep.findById( shop.getId() ).orElse(null);

        if (shop.getName() != null) existingShop.setName(shop.getName());
        if (shop.getShop_address() != null) existingShop.setShop_address(shop.getShop_address());
        if (shop.getBusiness() != null) existingShop.setBusiness(shop.getBusiness());
        if (shop.getDelivery() != null) existingShop.setDelivery(shop.getDelivery());

        return rep.save(existingShop);
    }
}