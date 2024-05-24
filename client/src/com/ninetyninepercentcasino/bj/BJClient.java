package com.ninetyninepercentcasino.bj;

import com.ninetyninepercentcasino.net.BJBetRequest;
import com.ninetyninepercentcasino.net.BJCardUpdate;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.Socket;

/**
 * client of a blackjack game that receives messages from the server and handles them
 */
public class BJClient extends Connection {

    public BJClient(Socket clientSocket) throws IOException {
        super(clientSocket);
    }

    @Override
    public void run(){
        try {
            while (alive) {
                if(!clientSocket.isConnected()) {
                    finish();
                }
                try {
                    NetMessage message = (NetMessage) in.readObject();
                    message.setOrigin(clientSocket.getRemoteSocketAddress());
                    if (message.getContent() != null) {
                        System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
                        switch(message.getType()) {
                            case ACK:
                                aliveMessage = (String) message.getContent();
                                break;
                            case PING:
                                message.setType(NetMessage.MessageType.ACK);
                                out.writeObject(message);
                                break;
                            case INFO:
                                if(message.getContent() instanceof BJBetRequest) {
                                    ((BJBetRequest)message.getContent()).setAmountBet(19);
                                    out.writeObject(message);
                                }
                                else if(message.getContent() instanceof BJCardUpdate){
                                }
                            default:
                        }
                    }
                } catch (OptionalDataException e) {

                } catch (EOFException e) {
                    finish();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
