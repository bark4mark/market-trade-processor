package co.markhoward.messagetradeprocessor.trademessage;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.markhoward.messagetradeprocessor.currency.CurrencyRepository;

/**
 * Controller for trade messages
 */
@RestController
public class TradeMessageController 
{
	private final Publisher<TradeMessage> publisher;
	private final CurrencyRepository currencyRepository;
	
	private TradeMessage latest;
	
	@Autowired
	public TradeMessageController (Publisher<TradeMessage> publisher, CurrencyRepository currencyRepository)
	{
		this.publisher = publisher;
		this.currencyRepository = currencyRepository;
	}
	
	/**
	 * Accept trade message and publish it to subscribed classes
	 * @param tradeMessage The message
	 * @throws IllegalArgumentException If validation fails
	 */
	@RequestMapping(method=RequestMethod.POST, value="/api/trademessage")
	@ResponseStatus(value=HttpStatus.OK)
	public void consumeTradeMessage (@RequestBody TradeMessage tradeMessage) throws IllegalArgumentException
	{
		validateMessage(tradeMessage);
		publisher.publish(tradeMessage);
		latest = tradeMessage;
	}
	
	/**
	 * Return the latest {@link TradeMessage}
	 * @return The latest trade message
	 */
	@RequestMapping(method=RequestMethod.GET, value="/api/trademessage/live")
	@ResponseStatus(value=HttpStatus.OK)
	public TradeMessage liveUpdate ()
	{
		return latest;
	}
	
	/**
	 * Get the current {@link Currency} that has the most buys and sells
	 * @return currency stats
 	 */
	@RequestMapping(method=RequestMethod.GET, value="/api/trademessage/currencystats")
	@ResponseStatus(value=HttpStatus.OK)
	@Transactional
	public Map<String, String> getCurrencyStats ()
	{
		return this.currencyRepository.getCurrencyStats();
	}
	
	private void validateMessage (TradeMessage message) throws IllegalArgumentException
	{
		if (valueIsIncorrectLength(message.getCurrencyFrom(), 3))
			throw new IllegalArgumentException("currencyFrom is incorrect length");
		
		if (valueIsIncorrectLength(message.getCurrencyTo(), 3))
			throw new IllegalArgumentException("currencyTo is incorrect length");
		
		if (valueIsIncorrectLength(message.getOriginatingCountry(), 1))
			throw new IllegalArgumentException("originatingCountry is incorrect length");
	}
	
	private boolean valueIsIncorrectLength (String currency, int length)
	{
		if (currency.length() != length)
			return true;
		
		return false;
	}
}
