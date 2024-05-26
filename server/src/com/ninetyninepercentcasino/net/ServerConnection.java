package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.game.bj.BJGame;
import com.ninetyninepercentcasino.game.bj.BJPlayer;

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
                                        bjGame = new BJGame(new BJPlayer(new Account("REPLACE"), this));
                                        bjGame.start();
                                        System.out.println("GAME BEGUN");
                                    }
                                }
                                if(message.getContent() instanceof BJBetRequest) {
                                    synchronized(bjGame.getBjSynchronizer()) {
                                        bjGame.getBjSynchronizer().notify();
                                    }
                                    bjGame.setFirstBet(((BJBetRequest)message.getContent()).getAmountBet());
                                    System.out.println("BET PLACED: " + ((BJBetRequest)message.getContent()).getAmountBet());
                                }
                                else if(message.getContent() instanceof BJActionUpdate){
                                    synchronized(bjGame.getBjSynchronizer()) {
                                        bjGame.getBjSynchronizer().notify();
                                    }
                                    bjGame.setAction(((BJActionUpdate) message.getContent()).getChosenAction());
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
