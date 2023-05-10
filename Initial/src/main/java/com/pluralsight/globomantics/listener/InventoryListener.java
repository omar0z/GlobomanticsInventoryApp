package com.pluralsight.globomantics.listener;

import com.pluralsight.globomantics.services.Service;

import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
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