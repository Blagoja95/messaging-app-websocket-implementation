package src.main.java.com.messaging.encoders;

import com.google.gson.Gson;
import src.main.java.com.messaging.models.message.Message;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>
{
	private static Gson gson = new Gson();

	@Override
	public String encode(Message message) throws EncodeException
	{
		return gson.toJson(message);
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
		// Custom initialization logic
	}

	@Override
	public void destroy() {
		// Close resources
	}
}
