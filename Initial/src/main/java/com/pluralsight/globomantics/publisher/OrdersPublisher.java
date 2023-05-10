package com.pluralsight.globomantics.publisher;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;


@Singleton
public class OrdersPublisher {


	private String[] productTypes = { "Nuts", "Bolts", "Nails", "Screws" };

	@Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
	public void sendOrderInformation() {


	}

}