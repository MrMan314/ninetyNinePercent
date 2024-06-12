package com.ninetyninepercentcasino.net
public class NetMessage implements Serializable:
	public enum MessageType:
		PING,
		ACK,
		INFO,
		NORMAL,
		ERROR
	private MessageType type
	private Object content
	private SocketAddress origin
	public NetMessage(MessageType type, Object content):
		this.type = type
		this.content = content
	public NetMessage(MessageType type, Object content, SocketAddress origin):
		this.type = type
		this.content = content
		this.origin = origin
	public MessageType getType():
		return type
	public void setType(MessageType type):
		this.type = type
	public Object getContent():
		return content
	public void setContent(Object content):
		this.content = content
	public SocketAddress getOrigin():
		return origin
	public void setOrigin(SocketAddress origin):
		this.origin = origin
