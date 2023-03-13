package com.pluralsight.globomantics.publisher;

import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

@Singleton
public class RestockPublisher {

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory jmsFactory;

	@Resource(lookup = "jms/restock")
	private Topic jmsTopic;

	public void sendRestockOrder(String restockOrder) {

		TextMessage message;

		try (Connection connection = jmsFactory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(jmsTopic)) {

			message = session.createTextMessage();
			message.setText(restockOrder);
			producer.send(message);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
    
}
