/**
 * ServerConnection.java
 * ServerConnection object, child of Connection
 */

package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.database.Database;
import com.ninetyninepercentcasino.database.UserAlreadyExists;
import com.ninetyninepercentcasino.database.PasswordIncorrect;
import com.ninetyninepercentcasino.database.AccountNonExistent;
import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.game.bj.BJGame;
import com.ninetyninepercentcasino.game.bj.BJPlayer;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.sql.SQLException;

public class ServerConnection extends Connection {
	private BJGame bjGame;
	private Database database;

	/**
	 * Constructor for ServerConnection, calls parent constructor
	 * pre: clientSocket and list of clients exists
	 * post: ServerConnection and Connection is started
	 */
	public ServerConnection(Socket clientSocket, List<Connection> clients, Database database) throws IOException {
		super(clientSocket, clients);
		this.database = database;
	}

	/**
	 * Main method for ServerConnection thread
	 * pre: ServerConnection is started
	 * post: ServerConnection is run
	 */
	public void run() {
		try {
			// Loop until dead
			while (alive) {
				// If connection is closed, quit
				if (!clientSocket.isConnected()) {
					finish();
				}
				try {
					// Read message from input stream
					NetMessage message = (NetMessage) in.readObject();
					// Set the origin of the message on the server side
					message.setOrigin(clientSocket.getRemoteSocketAddress());
					// Run if the message is not bogus
					if (message.getContent() != null) {
						// Log the message to the console
						switch(message.getType()) {
							case ACK:
								// Set the aliveMessage to the content of the message
								aliveMessage = (String) message.getContent();
								break;
							case PING:
								// Set the type of the message to ACK and send it back
								message.setType(NetMessage.MessageType.ACK);
								synchronized (out) {
									out.writeObject(message);
								}
								break;
							case INFO:
								System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
								// Read content of message
								Object content = message.getContent();
								if (content instanceof BJBeginGame) {
									bjGame = new BJGame(new BJPlayer(new Account("REPLACE"), this)); //TODO accounts
									bjGame.start();
								}
								else if (content instanceof BJBetMessage) {
									// Process BJBetMessage if it is a BJBetMessage
									bjGame.setFirstBet(((BJBetMessage)content).getAmountBet());
									synchronized (bjGame.getBjSynchronizer()) {
										bjGame.getBjSynchronizer().notify();
									}
								}
								else if (content instanceof BJInsuranceMessage) {
									bjGame.setInsuranceBet(((BJInsuranceMessage) content).getInsureAmount());
									synchronized (bjGame.getBjSynchronizer()) {
										bjGame.getBjSynchronizer().notify();
									}
								} else if (content instanceof BJActionUpdate) {
									// Update action, notify synchronizer
									bjGame.setAction(((BJActionUpdate)content).getChosenAction());
									synchronized (bjGame.getBjSynchronizer()) {
										bjGame.getBjSynchronizer().notify();
									}
								} else if (content instanceof LoginRequest) {
									NetMessage loginMessage;
									try {
										LoginRequest request = (LoginRequest) content;
										Account account = database.loadUser(request.getUsername(), request.getPassword());
										loginMessage = new NetMessage(NetMessage.MessageType.NORMAL, account);
									} catch (SQLException e) {
										loginMessage = new NetMessage(NetMessage.MessageType.ERROR, new ServerError());
									} catch (AccountNonExistent | PasswordIncorrect e) {
										loginMessage = new NetMessage(NetMessage.MessageType.ERROR, new LoginError());
									}
									synchronized (out) {
										out.writeObject(loginMessage);
									}
								} else if (content instanceof CreateUserRequest) {
									NetMessage creationMessage;
									try {
										LoginRequest request = (LoginRequest) content;
										Account account = database.createUser(request.getUsername(), request.getPassword());
										creationMessage = new NetMessage(NetMessage.MessageType.NORMAL, account);
									} catch (SQLException | AccountNonExistent | PasswordIncorrect e) {
										creationMessage = new NetMessage(NetMessage.MessageType.ERROR, new ServerError());
									} catch (UserAlreadyExists e) {
										creationMessage = new NetMessage(NetMessage.MessageType.ERROR, new CreateUserError());
									}
								}
							default:
						}
					}
				} catch (OptionalDataException e) {
					// This error can be safely ignored.
				} catch (SocketException | EOFException e) {
					// This occurs as a result of the client disconnectiong
					finish();
				} catch (IOException | ClassNotFoundException e) {
					// These errors cannot be ignored
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		try {
			bjGame.interrupt();
		} catch (NullPointerException e) {
		}
	}
}
