package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Request;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	final private static int DEFAULT_PORT = 5555;

	

	public static int getDefaultPort() {
		return DEFAULT_PORT;
	}

	public EchoServer(int port) {
		super(port);
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg == null)
			return;
		ArrayList<String> arr = new ArrayList<String>(Arrays.asList(((String) msg).split(",")));
		switch (arr.get(0)) {
		case "CONNECTED": {
			try {
				client.sendToClient("Connected to the server: " + client);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		}
		case "ADDLINE": {
			String str = arr.subList(1, arr.size()).toString();
			str = str.substring(1, str.length() - 1);
			try {
				DataBaseController.addToDB(str);
			} catch (Exception e) {
				System.out.println("Could not add to db");
			}
			try {
				client.sendToClient("ADD: added to db!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "REFRESH": {
			ObservableList<Request> ol = DataBaseController.getTable();
			try {
				client.sendToClient(ol.toArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "REFRESHID": {
			int id = Integer.parseInt(arr.get(1));
			ObservableList<Request> ol = DataBaseController.getTableWithID(id);
			try {
				client.sendToClient(ol.toArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "CHANGECHANGE": {
			String str = arr.subList(1, arr.size()).toString();
			str = str.substring(1, str.length() - 1);
			int id = Integer.parseInt(arr.get(1));
			str=str.substring(3);
			try {
				DataBaseController.changeChanges(id, str);
			} catch (Exception e) {
				System.out.println("Could not change in db");
			}
			try {
				client.sendToClient("CHANGES: changed in db!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "CHANGEDESC": {
			String str = arr.subList(1, arr.size()).toString();
			str = str.substring(1, str.length() - 1);
			int id = Integer.parseInt(arr.get(1));
			str=str.substring(3);
			try {
				DataBaseController.changeDescription(id, str);
			} catch (Exception e) {
				System.out.println("Could not change in db");
			}
			try {
				client.sendToClient("DESC: changed in db!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case "CHANGESTATUS": {
			String str = arr.subList(1, arr.size()).toString();
			str = str.substring(1, str.length() - 1);
			int id = Integer.parseInt(arr.get(1));
			str=str.substring(3);
			try {
				DataBaseController.changeStatus(id, str);
			} catch (Exception e) {
				System.out.println("Could not change in db");
			}
			try {
				client.sendToClient("STATUS: changed in db!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		}
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	public static int Start(int port) {
		if(DataBaseController.Connect()==false) {
			return 1;
		}
		
		EchoServer sv = new EchoServer(port);
		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
			return 2;
		}
		return 0;
	}
}