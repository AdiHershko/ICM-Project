package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.glass.ui.Application.EventHandler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
	Pane root;
	Stage stage;
	Controller c;
	TableView<Request> table;
	Media m = new Media(Main.class.getResource("alert.mp3").toExternalForm());
	MediaPlayer mp = new MediaPlayer(m);
    @Override
    public void start(Stage stage) throws Exception {
    	this.stage=stage;
		try { //loading fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmlFile.fxml"));
			root = loader.load();
			c = loader.getController(); //saving controller class
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("Mother Yukker requester");
		stage.show();
		DataBaseController.Connect();
		setTable();
		refreshTable();
		Button addButton = c.getAddButton();
		addButton.setOnAction(e->{
			try {
				DataBaseController.addToDB(c.gettAddText().getText().toString());
				refreshTable();
				c.gettAddText().setText("");
			}
			catch(Exception e1){
				mp.play();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wrong parameters!");
				alert.setContentText("Please enter 6 parameters: string,INT,string,string,string,string");
				alert.show();
			}
		});

		Button changeButton = c.getChangeButton();
		changeButton.setOnAction(e->{
			Request r;
		    r = table.getSelectionModel().getSelectedItem();
			try {
			r.setStatus(c.getChangeText().getText().toString());
			} catch(Exception e2)
			{
				mp.play();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("No row selected!");
				alert.show();
				return;
			}
			DataBaseController.changeStatus(r.getId(), r.getStatus());
			refreshTable();
		});


		Button saveDesc = c.getSaveDescButton();
		saveDesc.setOnAction(e->{
			Request r;
		    r = table.getSelectionModel().getSelectedItem();
			try {
			r.setDesc(c.getDescArea().getText().toString());
			} catch(Exception e2)
			{
				mp.play();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("couldn't save changes!");
				alert.show();
				return;
			}
			DataBaseController.changeDescription(r.getId(), r.getDesc());
			refreshTable();
			c.getDescArea().setEditable(false);
			c.getSaveDescButton().setVisible(false);
		});

		Button saveChanges = c.getSaveChangesButton();
		saveChanges.setOnAction(e->{
			Request r;
		    r = table.getSelectionModel().getSelectedItem();
			try {
			r.setChange(c.getChangesArea().getText().toString());
			} catch(Exception e2)
			{
				mp.play();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("couldn't save changes!");
				alert.show();
				return;
			}
			DataBaseController.changeChanges(r.getId(), r.getChange());
			refreshTable();
			c.getChangesArea().setEditable(false);
			c.getSaveChangesButton().setVisible(false);
		});


    }

    public void setTable()
    {
    	ObservableList<Request> o = DataBaseController.getTable();
		TableColumn<Request,Integer> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Request,String> nameColumn = new TableColumn<>("name");
		nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		nameColumn.setPrefWidth(90);
		TableColumn<Request,String> systemColumn = new TableColumn<>("system");
		systemColumn.setCellValueFactory(new PropertyValueFactory("system"));
		TableColumn<Request,String> statusColumn = new TableColumn<>("status");
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
		table = (TableView<Request>) c.getTable();
		table.getColumns().addAll(idColumn,nameColumn,systemColumn,statusColumn);
    }

    public void refreshTable()
    {
    	ObservableList<Request> ol = DataBaseController.getTable();
		table.setItems(ol);
    }







	public static void main(String[] args) {
		launch(args);
	}
}
