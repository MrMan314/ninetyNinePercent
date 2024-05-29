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
                                Object content = message.getContent();
                                if(content instanceof String){
                                    if(message.getContent().equals("begin game.")){
                                        bjGame = new BJGame(new BJPlayer(new Account("REPLACE"), this));
                                        bjGame.start();
                                        System.out.println("GAME BEGUN");
                                    }
                                }
                                if(content instanceof BJBetRequest) {
                                    bjGame.setFirstBet(((BJBetRequest)content).getAmountBet());
                                    synchronized(bjGame.getBjSynchronizer()) {
                                        bjGame.getBjSynchronizer().notify();
                                    }
                                    System.out.println("BET PLACED: " + ((BJBetRequest)content).getAmountBet());
                                }
                                else if(content instanceof BJActionUpdate){
                                    bjGame.setAction(((BJActionUpdate)content).getChosenAction());
                                    synchronized(bjGame.getBjSynchronizer()) {
                                        bjGame.getBjSynchronizer().notify();
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
