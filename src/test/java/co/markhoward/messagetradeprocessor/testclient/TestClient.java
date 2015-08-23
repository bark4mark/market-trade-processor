package co.markhoward.messagetradeprocessor.testclient;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import co.markhoward.messagetradeprocessor.trademessage.TradeMessage;

/**
 * Generate some random data and post it to the trade message end point
 */
public class TestClient 
{
	@Test
	@Ignore
	public void generateRequests () throws Exception
	{
		ObjectMapper objectMapper = new ObjectMapper ();
		HttpClient client = HttpClients.createDefault();
		
		while (true)
		{
			HttpPost httpPost = new HttpPost("http://localhost:8080/api/trademessage");
			httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(createTradeMessageWithRandomData()), ContentType.create("application/json")));
			client.execute(httpPost);
			
//			try 
//			{
//			    Thread.sleep(100);
//			} 
//			catch(InterruptedException exception) 
//			{
//			    Thread.currentThread().interrupt();
//			}
		}
	}
	
	private TradeMessage createTradeMessageWithRandomData ()
	{
		TradeMessage tradeMessage = new TradeMessage ();
		tradeMessage.setCurrencyFrom(getRandomCurrency());
		tradeMessage.setCurrencyTo(getRandomCurrency());
		tradeMessage.setOriginatingCountry(getRandomCountry ());
		tradeMessage.setAmountBuy(generateRandomAmount());
		tradeMessage.setAmountSell(generateRandomAmount());
		tradeMessage.setRate(generateRandomAmount());
		tradeMessage.setTimePlacedAsDate(new Date ());
		tradeMessage.setUserId(generateRandomUserId());
		return tradeMessage;
	}
	
	private String generateRandomUserId ()
	{
		Random r = new Random();
		return String.valueOf(r.nextInt(10000 - 1000) + 1000);
	}
	
	private double generateRandomAmount ()
	{
		Random r = new Random();
		return round(1 + (1000 - 1) * r.nextDouble());
	}
	
	private double round (double value)
	{
		long factor = (long) Math.pow(10, 2);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	private String getRandomCountry ()
	{
		Random r = new Random();
		return countries.get(r.nextInt(10 - 1) + 1);
	}
	
	private String getRandomCurrency ()
	{
		Random r = new Random();
		return currencies.get(r.nextInt(10 - 1) + 1);
	}
	
	private static Map<Integer, String> currencies = ImmutableMap.<Integer, String>builder()
			.put(1, "EUR")
			.put(2, "GBP")
			.put(3, "AUD")
			.put(4, "AZN")
			.put(5, "BND")
			.put(6, "CHE")
			.put(7, "COP")
			.put(8, "EGP")
			.put(9, "GMD")
			.put(10, "INR")
			.build();
	
	private static Map<Integer, String> countries = ImmutableMap.<Integer, String>builder()
			.put(1, "IE")
			.put(2, "UK")
			.put(3, "US")
			.put(4, "FR")
			.put(5, "DE")
			.put(6, "RU")
			.put(7, "PH")
			.put(8, "CN")
			.put(9, "DK")
			.put(10, "GH")
			.build();
}
