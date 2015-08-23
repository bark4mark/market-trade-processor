package co.markhoward.messagetradeprocessor.trademessage.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.markhoward.messagetradeprocessor.trademessage.Subscriber;
import co.markhoward.messagetradeprocessor.trademessage.TradeMessage;
import co.markhoward.messagetradeprocessor.trademessage.TradeMessageRepository;

@Component
public class PersistenceSubscriber implements Subscriber<TradeMessage>
{
	private final TradeMessageRepository tradeMessageRepository;

	@Autowired
	public PersistenceSubscriber (TradeMessageRepository tradeMessageRepository)
	{
		this.tradeMessageRepository = tradeMessageRepository;
	}
	
	@Override
	@Transactional
	public void subscribe(TradeMessage message) 
	{
		tradeMessageRepository.save(message);
	}

}
