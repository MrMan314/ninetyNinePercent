/**
 * ServerConnection.java
 * ServerConnection object, child of Connection
 */

package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.net.BJBetRequest;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerConnection extends Connection {
    private BJGame bjGame;

	/**
	 * Constructor for ServerConnection, calls parent constructor
	 * pre: clientSocket and list of clients exists
	 * post: ServerConnection and Connection is started
	 */
    public ServerConnection(Socket clientSocket, List<Connection> clients) throws IOException {
        super(clientSocket, clients);
    }

	/**
	 * Main method for ServerConnection thread
	 * pre: ServerConnection is started
	 * post: ServerConnection is run
	 */
    @Override
    public void run(){
        try {
			// Loop until dead
            while (alive) {
				// If connection is closed, quit
                if(!clientSocket.isConnected()) {
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
						System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
						switch(message.getType()) {						
							case ACK:
								// Set the aliveMessage to the content of the message
								aliveMessage = (String) message.getContent();
								break;
							case PING:
								// Set the type of the message to ACK and send it back
								message.setType(NetMessage.MessageType.ACK);
								out.writeObject(message);
                                break;
                            case INFO:
								// Start game if request says so
                                if(message.getContent() instanceof String){
                                    if(message.getContent().equals("begin game.")){
                                        bjGame = new BJGame();
                                    }
                                }
								// Process BJBetRequest if it is a BJBetRequest
                                if(message.getContent() instanceof BJBetRequest) {
                                    synchronized(bjGame.getBjSynchronizer()) {
                                        bjGame.getBjSynchronizer().notify();
                                    }
                                    bjGame.setFirstBet(((BJBetRequest)message.getContent()).getAmountBet());
                                }
                            default:
                        }
                    }
				} catch (OptionalDataException e) {
					// This error can be safely ignored.
				} catch (EOFException e) {
					// This occurs as a result of the 
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
}
