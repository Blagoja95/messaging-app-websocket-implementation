package src.main.java.com.messaging.models.message;

public class Message
{
	private String msgID;
	private String from;
	private String to;
	private String content;
	private String timestring;

	public String getTimestring()
    {
		return timestring;
	}

	public void setTimestring(String timestring)
    {
		this.timestring = timestring;
	}

	public String getMsgID()
    {
		return msgID;
	}

	public void setMsgID(String msgID)
    {
		this.msgID = msgID;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
