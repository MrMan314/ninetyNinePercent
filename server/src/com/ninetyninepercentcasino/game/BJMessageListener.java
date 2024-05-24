package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.game.BJSynchronizer;
import com.ninetyninepercentcasino.net.BJBetRequest;
import com.ninetyninepercentcasino.net.ServerConnection;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.Socket;

public class BJMessageListener extends ServerConnection {

    private final BJGame game;
    public BJMessageListener(Socket clientSocket, BJGame game) throws IOException {
        super(clientSocket);
        this.game = game;
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
                                    synchronized(game.getBjSynchronizer()) {
                                        game.getBjSynchronizer().notify();
                                    }
                                    game.setFirstBet(((BJBetRequest)message.getContent()).getAmountBet());
                                }
                                if(message.getContent() instanceof String) {
                                    if(message.getContent().equals("begin game.")) {
                                        game.startRound();
                                    }
                                }

                            default:
                        }
                    }
                } catch (OptionalDataException e) {

                } catch (EOFException e) {
                    finish();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
