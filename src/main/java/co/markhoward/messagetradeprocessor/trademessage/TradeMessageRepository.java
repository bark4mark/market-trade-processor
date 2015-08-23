package co.markhoward.messagetradeprocessor.trademessage;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TradeMessageRepository 
{
	private final EntityManager entityManager;
	
	@Autowired
	public TradeMessageRepository (EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public void save (TradeMessage tradeMessage)
	{
		this.entityManager.persist(tradeMessage);
	}
}
