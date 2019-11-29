package application;

import java.io.*;
import java.util.ArrayList;

import ocsf.server.*;

public class EchoServer extends AbstractServer {
	final public static int DEFAULT_PORT = 5555;

	public EchoServer(int port) {
		super(port);
		DataBaseController.Connect();
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof String) {
			System.out.println("Message received: " + msg + " from " + client);
			this.sendToAllClients(msg);//Works with object!
		}
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	public static void main(String[] args) {
		int port = 0;

		try {
			port = Integer.parseInt(args[0]);
		} catch (Throwable t) {
			port = DEFAULT_PORT;
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
