package co.markhoward.messagetradeprocessor.currency;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.markhoward.messagetradeprocessor.trademessage.TradeMessage;

@Repository
public class CurrencyRepository 
{
	private final EntityManager entityManager;
	
	@Autowired
	public CurrencyRepository (EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public void updateCurrencies (TradeMessage tradeMessage)
	{
		updateSold (tradeMessage);
		updateBought(tradeMessage);
	}
	
	public Map<String, String> getCurrencyStats ()
	{
		Map<String, String> currencyStats = new HashMap <> ();
		
		try
		{
			Query soldQuery = this.entityManager.createNativeQuery(MOST_SOLD_QUERY);
			String mostSoldCurrency = (String) soldQuery.getSingleResult();
			currencyStats.put(MOST_SOLD, mostSoldCurrency);
		
			Query purchasedQuery = this.entityManager.createNativeQuery(MOST_PURCHASED_QUERY);
			String mostPurchasedCurrency = (String) purchasedQuery.getSingleResult();
			currencyStats.put(MOST_PURCHASED, mostPurchasedCurrency);
			return currencyStats;
		}
		catch (NoResultException exception)
		{
			return currencyStats;
		}
		
		
	}
	
	private void updateSold (TradeMessage tradeMessage)
	{
		try
		{
			TypedQuery<Currency> query = this.entityManager.createQuery ("FROM Currency WHERE currency=:currency", Currency.class);
			query.setParameter ("currency", tradeMessage.getCurrencyFrom());
			Currency currency = query.getSingleResult();
			
			double currentSold = currency.getSold();
			currency.setSold(currentSold + tradeMessage.getAmountSell());
		}
		catch (NoResultException exception)
		{
			Currency newCurrency = new Currency ();
			newCurrency.setSold(tradeMessage.getAmountSell());
			newCurrency.setCurrency(tradeMessage.getCurrencyFrom());
			this.entityManager.persist(newCurrency);
		}
	}
	
	private void updateBought (TradeMessage tradeMessage)
	{
		try
		{
			TypedQuery<Currency> query = this.entityManager.createQuery ("FROM Currency WHERE currency=:currency", Currency.class);
			query.setParameter ("currency", tradeMessage.getCurrencyTo());
			Currency currency = query.getSingleResult();
			
			double currentBought = currency.getBought();
			currency.setBought(currentBought + tradeMessage.getAmountBuy());
		}
		catch (NoResultException exception)
		{
			Currency newCurrency = new Currency ();
			newCurrency.setBought(tradeMessage.getAmountBuy());
			newCurrency.setCurrency(tradeMessage.getCurrencyTo());
			this.entityManager.persist(newCurrency);
		}
	}
	
	public static final String MOST_SOLD = "mostSold";
	public static final String MOST_PURCHASED =  "mostPurchased";
	
	private final String MOST_SOLD_QUERY = "SELECT currency FROM Currency WHERE sold = (SELECT MAX(sold) FROM Currency)";
	private final String MOST_PURCHASED_QUERY = "SELECT currency FROM Currency WHERE bought = (SELECT MAX(bought) FROM Currency)";
}
