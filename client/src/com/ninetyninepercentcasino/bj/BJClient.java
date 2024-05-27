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
                                Object content = message.getContent();
                                if(content instanceof BJBetRequest) {
                                    ((BJBetRequest)content).setAmountBet(19);
                                    out.writeObject(message);
                                }
                                else if(content instanceof BJCardUpdate){
                                    screen.requestUpdate((DTO)content);
                                }
                                else if(content instanceof BJInsuranceRequest){

                                }
                                else if(content instanceof BJAvailActionUpdate){
                                    screen.requestUpdate((DTO)content);
                                }
                                else if(content instanceof BJSplit){
                                    screen.requestUpdate((DTO)content);
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
