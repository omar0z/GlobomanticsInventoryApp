package com.pluralsight.globomantics.listener;

import com.pluralsight.globomantics.services.Service;

import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;


public class ProductSupplier implements MessageListener {

    @Inject
    private Service productService;

    @Override
    public void onMessage(Message message) {
        
    }

}
