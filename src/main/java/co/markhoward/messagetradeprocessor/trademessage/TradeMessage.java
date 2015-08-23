package co.markhoward.messagetradeprocessor.trademessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Class to represent a trade message
 */
@Data
@Entity
public class TradeMessage 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String userId;
	private String currencyFrom;
	private String currencyTo;
	private double amountSell;
	private double amountBuy;
	private double rate;
	private String timePlaced;
	private String originatingCountry;
	
	/**
	 * Get the time placed parsed as a {@link Date}
	 * @return The Date
	 * @throws ParseException If there is a problem parsing the date
	 */
	@JsonIgnore
	public Date getTimePlacedAsDate () throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return simpleDateFormat.parse(this.timePlaced);
	}
	
	/**
	 * Set the time placed using a {@link Date}
	 * @param date The date to set
	 */
	@JsonIgnore
	public void setTimePlacedAsDate (Date date)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		this.timePlaced = simpleDateFormat.format(date);
	}
	
	private static final String DATE_FORMAT = "dd-MMM-yy hh:mm:ss";
}
