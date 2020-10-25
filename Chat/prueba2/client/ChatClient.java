import java.net.*;
import java.util.concurrent.Semaphore;
import java.io.*;


public class ChatClient {
	private String hostname;
	private int port;
	private String userName;

	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void execute() {
		try {
			Semaphore mutex = new Semaphore(0);
			Semaphore turno;
			Socket socket = new Socket(hostname, port);
			
			System.out.println("Connected to the chat server");
			ReadThread readThread;
			WriteThread writeThread;
			readThread = new ReadThread(socket, mutex);

			readThread.start();//thread
			try { mutex.acquire(); } catch (Exception e) { }
			String playerName = readThread.getPlayerName();
			//System.out.println("PLAYER NAME:"+playerName);
			turno = readThread.getSemaphoreTurn();
			writeThread = new WriteThread(socket, this, playerName);
			writeThread.setSemaphoreTurn(turno);
			writeThread.start();//thread

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}

	void setUserName(String userName) {
		this.userName = userName;
	}

	String getUserName() {
		return this.userName;
	}


	public static void main(String[] args) {
		

		String hostname ="localhost";
		int port = 8989;
		System.out.println(hostname);
		ChatClient client = new ChatClient(hostname, port);
		client.execute();
	}
}