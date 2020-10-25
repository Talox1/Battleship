package battleship.model.socket.client;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.concurrent.Semaphore;

public class ReadThread extends Observable implements Runnable{
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    private String playerName = "";
    private Semaphore turno;
    private Semaphore mutex;
    public ReadThread(Socket socket,  Semaphore mutex) {

        this.mutex = mutex;
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        try { 
            String response = reader.readLine();
            System.out.println("\n" + response);
            if(response.equals("No other users connected")) {
                playerName = "player 1";
            }else{				
                playerName = "player 2";
            }
            turno = new Semaphore(0);
            mutex.release(); 
        } catch (Exception e) { }

        while (true) {
            try {
                String serverMsg = reader.readLine();// read message from server
                System.out.println("\nServer msg >>" + serverMsg);//print message from server
                
                if(serverMsg.contains("rect_shoot") || serverMsg.contains("New user connected")){//enemy turn done, so u can attack
                    turno.release();
                    this.setChanged();
                    this.notifyObservers("ya es tu turno "+playerName);
                    
                    String data[] = serverMsg.strip().split(":");
                    
                    if(data.length > 2){
                        
                        System.out.println("ReadThread msg --> "+data[1].contains("destruiste mi barco")+" -- "+data[1] );
                        System.out.println("ReadThread msg --> "+serverMsg);
                        if(data[1].contains("Atacando")){// msg when the enemy attacks
                            this.setChanged();
                            this.notifyObservers("el enemigo te ataco:"+data[2]);
                        }
                       
                    }
                    
                }else if(serverMsg.contains("rect_base")){
                    String data[] = serverMsg.strip().split(":");
                    if(data[1].equals("destruiste mi barco")){//message when the enemy say´s that u destroyed his ship
                            
                            String id_rect_ship = data[2].replaceAll("rect_base","rect_shoot");
                            System.out.println("ReadThread msg --> "+id_rect_ship);
                            this.setChanged();
                            this.notifyObservers("destruiste un barco enemigo:"+id_rect_ship);
                        }
                }else if(serverMsg.contains("Has perdido")){
                    this.setChanged();
                    this.notifyObservers("He perdido la partida");
                }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }

    public String getPlayerName(){
        return playerName;
    }

    public Semaphore getSemaphoreTurn(){
        return turno;
    }
}