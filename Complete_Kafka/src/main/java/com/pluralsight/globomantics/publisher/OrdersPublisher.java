package com.pluralsight.globomantics.publisher;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Singleton
public class OrdersPublisher {

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory jmsFactory;

	@Resource(lookup = "jms/orders")
	private Queue jmsQueue;

	private String[] productTypes = { "Nuts", "Bolts", "Nails", "Screws" };

	@Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
	public void sendOrderInformation() {

		TextMessage message;

		try (Connection connection = jmsFactory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(jmsQueue)) {

			JsonObject orderInformation = Json.createObjectBuilder()
					.add("productType", productTypes[ThreadLocalRandom.current().nextInt(productTypes.length)])
					.add("quantity", ThreadLocalRandom.current().nextInt(1, 150))
					.add("timestamp", Instant.now().toEpochMilli()).build();

			message = session.createTextMessage();
			message.setText(orderInformation.toString());

			producer.send(message);
			System.out.println("Order submitted! Order: "+orderInformation.toString());

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
