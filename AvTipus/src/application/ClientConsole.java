package application;

import java.io.*;

import ocsf.client.AbstractClient;

public class ClientConsole  extends AbstractClient {
	final public static int DEFAULT_PORT = 5555;

	public ClientConsole(String host, int port) {
		super(host, port);
		try{
			openConnection();
		}catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}
	
	public void handleMessageFromServer(Object msg) {
		System.out.println("Client console got: " + msg.toString());
	}
	
	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				message = fromConsole.readLine();
				sendToServer(message);
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	public void display(String message) {
		System.out.println("> " + message);
	}

	public static void main(String[] args) {
		String host = "";

		try {
			host = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			host = "localhost";
		}
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(UserConsole.class);
			}
		}.start();
		ClientConsole chat = new ClientConsole(host, DEFAULT_PORT);
		chat.accept();
	}

}
