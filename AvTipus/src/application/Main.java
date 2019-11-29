package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	Pane root;
	Stage stage;
	Controller c;
	TableView<Request> table;

	@Override
	public void start(Stage stage) throws Exception {
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
		DataBaseController.Connect();
		setTable();
		refreshTable();

		c.getDescArea().setWrapText(true);
		c.getChangesArea().setWrapText(true);
		
		Button search = c.getSearchButton();
		search.setOnAction(e -> {
			try {
				int id = Integer.parseInt(c.getSerachFeild().getText());
				refreshTableWithID(id);
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText("You didn't enter a number");
				alert.show();
				return;
			}
		});

		Button addButton = c.getAddButton();
		addButton.setOnAction(e -> {
			try {
				DataBaseController.addToDB(c.gettAddText().getText().toString());
				refreshTable();
				c.gettAddText().setText("");
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wrong parameters!");
				alert.setContentText("Please enter 6 parameters:\n" + "string,INT,string,string,string,string\n"
						+ "The int is 0-5\n" + "The strings are up to 1000 characters");
				alert.show();
			}
		});

		Button saveDesc = c.getSaveDescButton();
		saveDesc.setOnAction(e -> {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			try {
				String text = c.getDescArea().getText();
				if (text.length() > 1000)
					throw new Exception("Too long text");
				r.setDesc(text);
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();
				e2.printStackTrace();
				return;
			}
			DataBaseController.changeDescription(r.getId(), r.getDesc());
			refreshTable();
			c.getDescArea().setEditable(false);
			c.getSaveDescButton().setVisible(false);
		});

		Button saveChanges = c.getSaveChangesButton();
		saveChanges.setOnAction(e -> {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			try {
				String text = c.getChangesArea().getText();
				if (text.length() > 1000)
					throw new Exception("Too long text");
				r.setChange(text);
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();
				e2.printStackTrace();
				return;
			}
			DataBaseController.changeChanges(r.getId(), r.getChange());
			refreshTable();
			c.getChangesArea().setEditable(false);
			c.getSaveChangesButton().setVisible(false);
		});

		Button saveStatus = c.getSaveStatusButton();
		saveStatus.setOnAction(e -> {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			try {
				String text = c.getStatusArea().getText();
				if (text.length() > 100)
					throw new Exception("Too long text");
				r.setStatus(text);
			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();
				e2.printStackTrace();
				return;
			}
			DataBaseController.changeStatus(r.getId(), r.getStatus());
			refreshTable();
			c.getStatusArea().setEditable(false);
			c.getSaveStatusButton().setVisible(false);
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setTable() {
		TableColumn<Request, Integer> idColumn = new TableColumn<>("Request ID");
		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Request, String> nameColumn = new TableColumn<>("Requestor Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		TableColumn<Request, String> systemColumn = new TableColumn<>("System");
		systemColumn.setCellValueFactory(new PropertyValueFactory("system"));
		TableColumn<Request, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory("status"));
		table = (TableView<Request>) c.getTable();
		table.getColumns().addAll(idColumn, nameColumn, systemColumn, statusColumn);
	}

	public void refreshTable() {
		ObservableList<Request> ol = DataBaseController.getTable();
		table.setItems(ol);
	}

	public void refreshTableWithID(int id) {
		ObservableList<Request> ol = DataBaseController.getTableWithID(id);
		table.setItems(ol);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
