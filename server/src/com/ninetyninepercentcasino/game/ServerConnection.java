package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.net.BJBetRequest;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerConnection extends Connection {
    private BJGame bjGame;
    public ServerConnection(Socket clientSocket) throws IOException {
        super(clientSocket);
    }
    public ServerConnection(Socket clientSocket, List<Connection> clients) throws IOException {
        super(clientSocket, clients);
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
                                if(message.getContent() instanceof String){
                                    if(message.getContent().equals("begin game.")){
                                        bjGame = new BJGame();
                                    }
                                }
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
