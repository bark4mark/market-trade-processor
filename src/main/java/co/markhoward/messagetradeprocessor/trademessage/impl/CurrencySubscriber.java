package co.markhoward.messagetradeprocessor.trademessage.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.markhoward.messagetradeprocessor.currency.CurrencyRepository;
import co.markhoward.messagetradeprocessor.trademessage.Subscriber;
import co.markhoward.messagetradeprocessor.trademessage.TradeMessage;

@Component
public class CurrencySubscriber implements Subscriber<TradeMessage> 
{
	private final CurrencyRepository currencyRepository;
	
	@Autowired
	public CurrencySubscriber (CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}
	
	@Override
	@Transactional
	public void subscribe(TradeMessage message) 
	{
		currencyRepository.updateCurrencies(message);
	}
	
}
