package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerConsole extends Application {
	public static ServerConsole _init;
	Pane root;
	static Stage stage;
	ServerChooseController c;
	


	@Override
	public void start(Stage stage) throws Exception {
		_init=this;
		this.stage = stage;
		try { // loading fxml file
			FXMLLoader load = new FXMLLoader();
			load.setLocation(getClass().getResource("servergui.fxml"));
			root = load.load();
			c = load.getController(); // saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		c.setChoiceBox();
		c.getServerPane().setVisible(false);
		
		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("ICM Prototype - Server");
		
		stage.show();
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
