package application;

import java.io.IOException;

import client.ChatClient;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import server.EchoServer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class UserConsole extends Application {
	public static UserConsole _init;
	Pane root;
	Stage stage;
	Controller c;
	TableView<Request> table;
	boolean isSerach = false;
	int searchid = 0;


	@Override
	public void start(Stage stage) throws Exception {
		_init=this;
		this.stage = stage;
		try { // loading fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlFile.fxml"));
			root = loader.load();
			c = loader.getController(); // saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("ICM - Prototype");
		stage.show();
		c.setTable();
		Controller._ins.client.handleMessageFromClientUI("REFRESH");
		c.getDescArea().setWrapText(true);
		c.getChangesArea().setWrapText(true);
		c.getChangesEditButton().setVisible(false);
		c.getDescEditButton().setVisible(false);
		c.getStatusEditButton().setVisible(false);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
