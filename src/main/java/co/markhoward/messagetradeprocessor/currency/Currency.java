package co.markhoward.messagetradeprocessor.currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * The currency entity
 */
@Data
@Entity
public class Currency 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(unique=true)
	private String currency;
	
	private double sold;
	
	private double bought;
}
