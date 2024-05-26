package com.ninetyninepercentcasino.bj;

import com.ninetyninepercentcasino.net.*;
import com.ninetyninepercentcasino.screens.BJScreen;

import java.io.EOFException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.Socket;

/**
 * client of a blackjack game that receives messages from the server and handles them
 */
public class BJClient extends Connection {

    private BJScreen screen;
    public BJClient(Socket clientSocket, BJScreen screen) throws IOException {
        super(clientSocket);
        this.screen = screen;
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
                                    screen.requestUpdate((DTO)message.getContent());
                                }
                                else if(message.getContent() instanceof BJInsuranceRequest){

                                }
                                else if(message.getContent() instanceof BJAvailActionUpdate){
                                    screen.requestUpdate((DTO)message.getContent());
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
