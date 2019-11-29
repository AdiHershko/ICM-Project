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

public class UserConsole extends Application {
	Pane root;
	Stage stage;
	Controller c;
	TableView<Request> table;

	boolean isSerach = false;
	int searchid = 0;

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

		c.getChangesEditButton().setVisible(false);
		c.getDescEditButton().setVisible(false);
		c.getStatusEditButton().setVisible(false);

		Button search = c.getSearchButton();
		search.setOnAction(e -> {
			try {
				String text = c.getSerachFeild().getText();
				if (text.equals("*")) {
					isSerach = false;
					refreshTable();
				} else {
					int id = Integer.parseUnsignedInt(text);
					isSerach = true;
					searchid = id;
					refreshTable();
				}
				c.getDescArea().setText("");
				c.getChangesArea().setText("");
				c.getHandlerLabel().setText("");
				c.getStatusArea().setText("");
				c.getIdLabel().setText("");

				c.getChangesEditButton().setVisible(false);
				c.getDescEditButton().setVisible(false);
				c.getStatusEditButton().setVisible(false);

				c.getSaveDescButton().setVisible(false);
				c.getSaveChangesButton().setVisible(false);
				c.getSaveStatusButton().setVisible(false);
				c.getDescArea().setEditable(false);
				c.getChangesArea().setEditable(false);
				c.getStatusArea().setEditable(false);
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
			int i = table.getSelectionModel().getSelectedIndex();
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
			table.getSelectionModel().select(i);
			c.getDescArea().setEditable(false);
			c.getSaveDescButton().setVisible(false);
		});

		Button saveChanges = c.getSaveChangesButton();
		saveChanges.setOnAction(e -> {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			int i = table.getSelectionModel().getSelectedIndex();
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
			table.getSelectionModel().select(i);
		});

		Button saveStatus = c.getSaveStatusButton();
		saveStatus.setOnAction(e -> {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			int i = table.getSelectionModel().getSelectedIndex();
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
			table.getSelectionModel().select(i);
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
		if (isSerach == false) {
			ObservableList<Request> ol = DataBaseController.getTable();
			table.setItems(ol);
		}
		if (isSerach) {
			ObservableList<Request> ol = DataBaseController.getTableWithID(searchid);
			table.setItems(ol);
		}
	}

}
