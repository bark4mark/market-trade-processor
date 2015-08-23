package co.markhoward.messagetradeprocessor.trademessage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TradeMessageTest
{
	TradeMessage tradeMessage;
	
	@Before
	public void setup () throws IOException
	{
		String providedJson = "{\"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-15 10:27:44\", \"originatingCountry\" : \"FR\"}";
		ObjectMapper mapper = new ObjectMapper ();
		tradeMessage = mapper.readValue(providedJson, TradeMessage.class);
	}
	
	@Test
	public void testThatCanBeCreatedFromProvidedJson ()
	{
		assertTrue(tradeMessage != null);
	}
	
	@Test
	public void testThatDatesCanBeParsed () throws ParseException
	{
		Date timePlaced = tradeMessage.getTimePlacedAsDate();
		assertTrue (timePlaced != null);
	}
}