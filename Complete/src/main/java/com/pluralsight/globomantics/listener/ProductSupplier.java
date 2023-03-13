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

@MessageDriven(name = "restockmdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/restock"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic") })
public class ProductSupplier implements MessageListener {

    @Inject
    private Service productService;

    @Override
    public void onMessage(Message message) {
        try {
            JsonObject restockRequest = Json.createReader(new StringReader(((TextMessage) message).getText()))
                    .readObject();

            String productType = restockRequest.getString("productType");

            System.out.println(String.format("Restocking %s ...",  productType));

            productService.update(productType);

        } catch (JMSException e) {
            e.printStackTrace(System.err);
        }
    }

}
