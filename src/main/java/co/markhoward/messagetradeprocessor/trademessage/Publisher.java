package co.markhoward.messagetradeprocessor.trademessage;

public interface Publisher <MessageType>
{
	/**
	 * Publishes the passed message to a set of subscribers
	 * @param message The message to publish
	 */
	void publish (MessageType message);
}
