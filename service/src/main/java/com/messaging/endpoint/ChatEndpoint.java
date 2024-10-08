package src.main.java.com.messaging.endpoint;

import src.main.java.com.messaging.decoders.MessageDecoder;
import src.main.java.com.messaging.encoders.MessageEncoder;
import src.main.java.com.messaging.models.message.Message;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.time.Instant;

@ServerEndpoint(
		value = "/chat/{username}",
		decoders = MessageDecoder.class,
		encoders = MessageEncoder.class
)
public class ChatEndpoint
{
	private Session session;
	private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException
	{
		this.session = session;
		chatEndpoints.add(this);
		users.put(session.getId(), username);

		Message message = new Message();
		message.setFrom("@special");
		message.setContent(username + " connected!");

		broadcast(message);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException
	{
	chatEndpoints.remove(this);
		Message message = new Message();
		message.setFrom(session.getId());
		message.setContent(users.get(session.getId()) + " disconnected!");

		users.remove(session.getId());

		broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) throws IOException, EncodeException
	{
		Message message = new Message();

		message.setFrom(users.get(session.getId()));
		message.setContent((throwable != null
				? throwable.getMessage()
				: "Something went wrong!"));

		broadcast(message);
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException
	{
		message.setFrom(users.get(session.getId()));
		message.setTimestring(Instant.now().toString());

		broadcast(message);
	}

	private static void broadcast(Message message) throws IOException, EncodeException
	{
		chatEndpoints.forEach(endpoint -> {
			synchronized (endpoint)
			{
				try
				{
					endpoint.session
							.getBasicRemote()
							.sendObject(message);
				}
				catch (IOException | EncodeException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
