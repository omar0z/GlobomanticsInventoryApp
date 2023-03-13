package com.pluralsight.globomantics.listener;

import java.io.StringReader;

import com.pluralsight.globomantics.services.Service;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.json.Json;
import jakarta.json.JsonObject;


public class ProductSupplier implements MessageListener {

    @Inject
    private Service productService;

    @Override
    public void onMessage(Message message) {
        
    }

}
