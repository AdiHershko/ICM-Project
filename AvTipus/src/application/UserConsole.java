package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserConsole extends Application {
	public static UserConsole _init;
	Pane root;
	static Stage stage;
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
		stage.setTitle("ICM Prototype - Client");
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        System.exit(0);
		    }
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
