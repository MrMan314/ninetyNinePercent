/**
 * NetMessage.java
 * Message object to send over the network
 */

package com.ninetyninepercentcasino.net;

import java.io.Serializable;
import java.net.SocketAddress;

public class NetMessage implements Serializable {
	// Message type identity
	public enum MessageType {
		PING,
		ACK,
		INFO,
		NORMAL,
		ERROR
	}

	// Private variables for the NetMessage
	private MessageType type;
	private Object content;
	private SocketAddress origin;

	/**
	 * Constructor for a NetMessage without an origin
	 * pre: none
	 * post: NetMessage object created
	 */
	public NetMessage(MessageType type, Object content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * Constructor for a NetMessage with an origin
	 * pre: none
	 * post: NetMessage object created
	 */
	public NetMessage(MessageType type, Object content, SocketAddress origin) {
		this.type = type;
		this.content = content;
		this.origin = origin;
	}

	/**
	 * Accessor method for type
	 * pre: NetMessage is initalized
	 * post: type of message is returned
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * Modifier method for type
	 * pre: NetMessage is initalized
	 * post: type of message is modified
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * Accessor method for content
	 * pre: NetMessage is initalized
	 * post: content is returned
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * Modifier method for content
	 * pre: NetMessage is initalized
	 * post: content of message is modified
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 * Accessor method for origin
	 * pre: NetMessage is initalized
	 * post: origin is returned
	 */
	public SocketAddress getOrigin() {
		return origin;
	}

	/**
	 * Modifier method for origin
	 * pre: NetMessage is initalized
	 * post: origin of message is modified
	 */
	public void setOrigin(SocketAddress origin) {
		this.origin = origin;
	}
}
