package com.pluralsight.globomantics.services;

import com.pluralsight.globomantics.entities.Products;
import com.pluralsight.globomantics.listener.ProductSupplier;
import com.pluralsight.globomantics.publisher.RestockPublisher;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ProductBean implements Service {

    @PersistenceContext
    EntityManager em;

    @Inject
    RestockPublisher restockPublisher;

    @Inject
    ProductSupplier supplier;

    @Resource
    ManagedExecutorService executor;

    @PostConstruct
    public void initSupplierForRestock(){
        System.out.println("Initializing supplier listener...");
        executor.submit(() -> {supplier.run();});
    }
    
    @Override
    public String fetch(Object... args) {

        String productType = (String) args[0];
        Integer quantity = (Integer) args[1];

        Products product = this.findBy(productType);

        int oldStockQuantity = product.getQuantity();

        if (oldStockQuantity < quantity) {

            System.out.println("Sorry!! There is not enough stock to fulfill the order!. Sending restock order...");
            System.out.println("Emmitting message to kafka...");
            this.emit(productType);
            return "Restock order sent. Waiting for supplier...";

        } else {

            int newStockQuantity = oldStockQuantity - quantity;
            product.setQuantity(newStockQuantity);
            em.persist(product);
            return String.format("Order fulfilled - %d %s has been taken out of stock. %d %s remain available.",
                    quantity, productType, newStockQuantity, productType);
        }
    }

    @Override
    public Products findBy(String field) {
        return (Products) em
                .createQuery(String.format("select p from Products p where p.productType = '%s'", field))
                .getSingleResult();
    }

    @Override
    public void update(String productType) {

        Products product = this.findBy(productType);
        product.setQuantity(Products.STOCK_QUANTITY);
        em.persist(product);
    }

    @Override
    public void emit(Object... args) {

        String productType = (String) args[0];

        JsonObject restockInformation = Json.createObjectBuilder()
                .add("productType", productType)
                .add("quantity", Products.STOCK_QUANTITY).build();

        restockPublisher.sendRestockOrder(restockInformation.toString());
    }

    

}
