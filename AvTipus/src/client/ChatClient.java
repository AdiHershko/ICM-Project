// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import java.io.IOException;

import application.Controller;
import application.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient {

	final public static int DEFAULT_PORT = 5555;

	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("Client connected");
	}

	public void handleMessageFromServer(Object msg) {
		if (msg == null)
			return;
		if (msg instanceof String) {
			System.out.println("from server: " + msg);
		}
		if (msg instanceof Object[]) {
			/*
			 * Platform.runLater(new Runnable() { TODO
			 * 
			 * @Override public void run() { ObservableList<Request> l =
			 * FXCollections.observableArrayList(); for (Object o : (Object[]) msg) {
			 * l.add((Request) o); } Controller._ins.table.setItems(l); } });
			 */
			ObservableList<Request> l = FXCollections.observableArrayList();
			for (Object o : (Object[]) msg) {
				l.add((Request) o);
			}
			Controller._ins.table.setItems(l);
			Controller.ref_mutex = false;
		}
	}

	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			quit();
		}
	}

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
		
	}
}
