package battleship.model.socket.client;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class ReadThread extends Thread {
	private BufferedReader reader;
	private String playerName = "";
	private Semaphore turno;
	private Semaphore mutex;
	public ReadThread(Socket socket, Semaphore mutex) {
		
		
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
				String mgs = reader.readLine();// read message from server
				System.out.println("\n>>" + mgs);//print message
				turno.release();
				
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