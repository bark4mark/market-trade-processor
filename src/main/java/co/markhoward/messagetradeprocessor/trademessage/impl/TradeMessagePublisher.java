package co.markhoward.messagetradeprocessor.trademessage.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.markhoward.messagetradeprocessor.trademessage.Publisher;
import co.markhoward.messagetradeprocessor.trademessage.Subscriber;
import co.markhoward.messagetradeprocessor.trademessage.TradeMessage;

@Component
public class TradeMessagePublisher implements Publisher<TradeMessage>
{
	private final Set<Subscriber<TradeMessage>> subscribers;
	
	@Autowired
	public TradeMessagePublisher (Set<Subscriber<TradeMessage>> subscribers)
	{
		this.subscribers = subscribers;
	}
	
	@Override
	public void publish(final TradeMessage tradeMessage) 
	{
		for (Subscriber<TradeMessage> subscriber:subscribers)
			subscriber.subscribe(tradeMessage);
	}
}
