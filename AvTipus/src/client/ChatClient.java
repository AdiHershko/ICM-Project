// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import ocsf.client.*;
import ocsf.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.Controller;
import application.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatClient extends AbstractClient {

	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("Client connected");
	}

	public void handleMessageFromServer(Object msg) {
		if (msg == null)
			return;
		System.out.println("from server: " + msg);
		if (msg instanceof Object[]) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ObservableList<Request> l = FXCollections.observableArrayList();
					for (Object o : (Object[]) msg) {
						l.add((Request) o);
					}
					Controller._ins.table.setItems(l);
				}
			});
		}
	}

	public void handleMessageFromClientUI(Object message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			System.out.println("sending message to server failed");
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
