package co.markhoward.messagetradeprocessor.trademessage;

public interface Subscriber<MessageType> 
{
	/**
	 * Subscribe to a message
	 * @param message The message recieved from the publisher
	 */
	void subscribe (MessageType message);
}
