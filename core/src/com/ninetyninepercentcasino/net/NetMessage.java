package com.ninetyninepercentcasino.net;

import java.io.Serializable;

public class NetMessage implements Serializable {
	enum MessageType {
		PING,
		ACK,
		INFO,
		NORMAL
	}

	MessageType type;
	String message;

	public NetMessage(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}
