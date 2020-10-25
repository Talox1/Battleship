import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class WriteThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;
	private Semaphore turno;
	private String playerName;
	public WriteThread(Socket socket, ChatClient client, String playername) {
		this.socket = socket;
		this.client = client;
		this.playerName = playername;
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {

		Console console = System.console();

		String userName = playerName;
		client.setUserName(userName);
		writer.println(userName);

		String text;

		do {
			try { turno.acquire(); } catch (Exception e) { }

			text = console.readLine("\n[" + userName + "]: can write ");//write what you want
			writer.println(text);

		} while (!text.equals("bye"));

		try {
			socket.close();
		} catch (IOException ex) {

			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}

	public void setSemaphoreTurn(Semaphore turno){
		this.turno = turno;
	}
}