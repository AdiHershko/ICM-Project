// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import java.awt.JobAttributes.DialogType;
import java.io.IOException;

import application.Controller;
import application.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;

public class ChatClient extends AbstractClient {

	final public static int DEFAULT_PORT = 5555;
	Dialog<Boolean> noConnection = new Dialog();

	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("Client connected");
		//-----------Checking if server is connected every 5 seconds.
		new Thread(){
			public void run()
			{
				while (true)
				{
					if (!isConnected())
					{
						Platform.runLater(new Runnable(){
							public void run()
							{
								{
									if (!noConnection.isShowing())
									{
										noConnection.setTitle("ERROR!");
										noConnection.setContentText("Server disconnected\nTrying to reconnect...");
										noConnection.show();
									}
								}
							}
						});
						try {
							while (!openConnection()) {
								try {Thread.sleep(3000);  }catch(InterruptedException e1) {}
							}
						} catch (IOException e) {  }

						Platform.runLater(new Runnable(){
							public void run()
							{
							Alert connected = new Alert(AlertType.INFORMATION);
							connected.setTitle("Reconnected!");
							connected.setContentText("Reconnected to server!");
							noConnection.setResult(Boolean.TRUE);
							noConnection.close();
							connected.show();
							}
						});

					}
					try{
						Thread.sleep(5000);
					} catch(InterruptedException e) {}

				}
			}
		}.start();
   //-------------------------------------------------
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("Server disconnected\nExiting application");
			alert.showAndWait();
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
