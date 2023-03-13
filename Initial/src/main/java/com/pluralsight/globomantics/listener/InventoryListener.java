package com.pluralsight.globomantics.listener;

import java.io.StringReader;

import com.pluralsight.globomantics.entities.Orders;
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
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class InventoryListener implements MessageListener {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private Service productService;

	@Override
	public void onMessage(Message message) {

		
	}

}
