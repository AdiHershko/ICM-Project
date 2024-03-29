package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Controller {

	public static boolean ref_mutex = false;

	public static Controller _ins;
	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private Pane pane;

	@FXML
	public TableView<Request> table;

	public Label getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(Label idLabel) {
		this.idLabel = idLabel;
	}

	public TextField getSerachFeild() {
		return serachFeild;
	}

	public void setSerachFeild(TextField serachFeild) {
		this.serachFeild = serachFeild;
	}

	public Button getSearchButton() {
		return SearchButton;
	}

	public void setSearchButton(Button searchButton) {
		SearchButton = searchButton;
	}

	@FXML
	private Button addButton;

	@FXML
	private Button ClientConnect;

	@FXML
	private TextField addText;

	@FXML
	private TextField IPTextBox;
	@FXML
	private RadioButton remoteRB;
	@FXML
	private RadioButton localRB;
	@FXML
	private Text descLimitText;
	@FXML
	private Text changesLimitText;
	@FXML
	private Pane requestPane;
	@FXML
	private Pane mainPane;
	@FXML
	private Pane loginPane;

	@FXML
	private TextArea descArea;

	@FXML
	private TextArea changesArea;

	@FXML
	private Label handlerLabel;

	@FXML
	private Button descEditButton;

	@FXML
	private Button changesEditButton;

	@FXML
	private Button saveDescButton;

	@FXML
	private Button saveChangesButton;

	@FXML
	private Label idLabel;

	@FXML
	private TextField serachFeild;

	@FXML
	private Button SearchButton;
	@FXML
	private Button choiceBoxEdit;

	ChatClient client;

	public void initialize() throws IOException {
		Controller._ins = this;
		loadData();
		/*
		 * This counts words in the text fields and prevents users from entering more
		 * characters than db limit.
		 */
		new Thread() {
			public void run() {
				while (true) {
					if (descLimitText.getText().length() >= 0 || changesLimitText.getText().length() >= 0) {
						try {
							descLimitText.setText(descArea.getText().length() + "/1000");
							changesLimitText.setText(changesArea.getText().length() + "/1000");
							if (descArea.getText().length() > 1000) {
								descArea.setEditable(false);
								descArea.setText(descArea.getText().substring(0, 1000));
								descArea.setEditable(true);
								descArea.positionCaret(1000);
							}
							if (changesArea.getText().length() > 1000) {
								changesArea.setEditable(false);
								changesArea.setText(changesArea.getText().substring(0, 1000));
								changesArea.setEditable(true);
								changesArea.positionCaret(1000);
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}.start();
	}

	@FXML
	public void connectToServer() {
		boolean isconnected = false;
		try {
			if (localRB.isSelected()) {
				client = new ChatClient("localhost", ChatClient.DEFAULT_PORT);
			}
			if (remoteRB.isSelected()) {
				String[] temp = IPTextBox.getText().split(":");
				client = new ChatClient(temp[0], Integer.parseInt(temp[1]));
			}
			isconnected = true;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Can't connect to server!");
			alert.setContentText("Can't connect to server!");
			alert.show();
		}
		if (isconnected) {
			mainPane.setVisible(true);
			loginPane.setVisible(false);
			setTable();
			refreshTable();
			getDescArea().setWrapText(true);
			getChangesArea().setWrapText(true);
			getChangesEditButton().setVisible(false);
			getDescEditButton().setVisible(false);
			getchoiceBoxEdit().setVisible(false);
		}

	}

	public Button getDescEditButton() {
		return descEditButton;
	}

	public Button getChangesEditButton() {
		return changesEditButton;
	}

	public Button getSaveDescButton() {
		return saveDescButton;
	}

	public Button getchoiceBoxEdit() {
		return choiceBoxEdit;
	}

	public Button getSaveChangesButton() {
		return saveChangesButton;
	}

	public Label getHandlerLabel() {
		return handlerLabel;
	}

	public TextArea getChangesArea() {
		return changesArea;
	}

	public TextArea getDescArea() {
		return descArea;
	}

	public Pane getRequestPane() {
		return requestPane;
	}

	public TextField gettAddText() {
		return addText;
	}

	public Button getAddButton() {
		return addButton;
	}

	public TableView<?> getTable() {
		return table;
	}

	@FXML
	private ChoiceBox<String> choiceBox;

	public ChoiceBox<String> getChoiceBox() {
		return choiceBox;
	}

	@FXML
	public void showRequest(MouseEvent event) {
		Request r;
		try {
			r = table.getSelectionModel().getSelectedItem();
			getDescArea().setText(r.getDesc());
			getChangesArea().setText(r.getChange());
			getHandlerLabel().setText(r.getHandler());
			getIdLabel().setText(Integer.toString(r.getId()));
			getChangesEditButton().setVisible(true);
			getDescEditButton().setVisible(true);
			getchoiceBoxEdit().setVisible(true);

			getSaveDescButton().setVisible(false);
			getSaveChangesButton().setVisible(false);
			getDescArea().setEditable(false);
			getChangesArea().setEditable(false);
			getChoiceBox().setValue(r.getStatus());
		} catch (Exception e) {
			return;
		}

	}

	@FXML
	public void editDesc() {
		getDescArea().setEditable(true);
		getSaveDescButton().setVisible(true);
		descLimitText.setVisible(true);
	}

	@FXML
	public void editChanges() {
		getChangesArea().setEditable(true);
		getSaveChangesButton().setVisible(true);
		changesLimitText.setVisible(true);
	}

	@FXML
	public void addToTable() {
		try {
			String txt = gettAddText().getText().toString();
			ArrayList<String> status = new ArrayList<String>();
			status.add("Active");
			status.add("Closed");
			status.add("Frozen");

			ArrayList<String> arr = new ArrayList<>(Arrays.asList(txt.split(",")));
			if (arr.size() != 6 || Integer.parseInt(arr.get(1)) > 5 || Integer.parseInt(arr.get(1)) < 0
					|| arr.get(0).length() > 100 || arr.get(2).length() > 1000 || arr.get(3).length() > 1000
					|| arr.get(4).length() > 100 || arr.get(5).length() > 100 || !status.contains(arr.get(4))) {
				throw new Exception("Wrong parameters");
			}
			client.handleMessageFromClientUI("ADDLINE," + txt);
			refreshTable();
			gettAddText().setText("");
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong parameters!");
			alert.setContentText(
					"Please enter 6 parameters:\n" + "string,INT,string,string,string,string\n" + "The int is 0-5\n"
							+ "The strings are up to 1000 characters\nThe status is Active | Closed | Frozen");
			alert.show();
		}
	}

	@FXML
	public void saveChangesChanges() {
		Request r;
		r = table.getSelectionModel().getSelectedItem();
		int i = table.getSelectionModel().getSelectedIndex();
		try {
			String text = getChangesArea().getText().toString();
			if (text.length() > 1000)
				throw new Exception("Too long text");
			r.setChange(text);
			client.handleMessageFromClientUI("CHANGECHANGE," + r.getId() + "," + text);
		} catch (Exception e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
			alert.show();

			return;
		}
		refreshTable();
		table.getSelectionModel().select(i);
		getChangesArea().setEditable(false);
		getSaveChangesButton().setVisible(false);
		changesLimitText.setVisible(false);
	}

	@FXML
	public void saveDescChanges() {
		Request r;
		r = table.getSelectionModel().getSelectedItem();
		int i = table.getSelectionModel().getSelectedIndex();
		try {
			String text = getDescArea().getText();
			if (text.length() > 1000)
				throw new Exception("Too long text");
			r.setDesc(text);
			client.handleMessageFromClientUI("CHANGEDESC," + r.getId() + "," + text);
		} catch (Exception e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
			alert.show();

			return;
		}
		refreshTable();
		table.getSelectionModel().select(i);
		getDescArea().setEditable(false);
		getSaveDescButton().setVisible(false);
		descLimitText.setVisible(false);
	}

	public void refreshTable() {
		ref_mutex = true;
		if (UserConsole._init.isSerach == false) {
			client.handleMessageFromClientUI("REFRESH");
		}
		if (UserConsole._init.isSerach) {
			client.handleMessageFromClientUI("REFRESHID," + UserConsole._init.searchid);
		}
		while (ref_mutex) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void searchTable() {
		try {
			String text = getSerachFeild().getText();
			if (text.equals("")) {
				UserConsole._init.isSerach = false;

			} else {
				int id = Integer.parseUnsignedInt(text);
				UserConsole._init.isSerach = true;
				UserConsole._init.searchid = id;
			}
			refreshTable();
			getDescArea().setText("");
			getChangesArea().setText("");
			getHandlerLabel().setText("");

			getIdLabel().setText("");

			getChangesEditButton().setVisible(false);
			getDescEditButton().setVisible(false);

			getSaveDescButton().setVisible(false);
			getSaveChangesButton().setVisible(false);
			getchoiceBoxEdit().setVisible(false);
			getDescArea().setEditable(false);
			getChangesArea().setEditable(false);

		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("You didn't enter a number");
			alert.show();
			return;
		}
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
		table = (TableView<Request>) getTable();
		table.getColumns().addAll(idColumn, nameColumn, systemColumn, statusColumn);
		client.handleMessageFromClientUI("CONNECTED");
	}

	private void loadData() {
		list.removeAll(list);
		String a = "Active";
		String b = "Closed";
		String c = "Frozen";
		list.addAll(a, b, c);
		choiceBox.getItems().addAll(list);
	}

	public void savingStatus(ActionEvent event) {
		if (choiceBox.getValue().equals("Active")) {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			int i = table.getSelectionModel().getSelectedIndex();
			try {
				String text = "Active";
				r.setStatus(text);
				client.handleMessageFromClientUI("CHANGESTATUS," + r.getId() + "," + text);

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();

				return;
			}
			refreshTable();
			table.getSelectionModel().select(i);

		}
		if (choiceBox.getValue().equals("Closed")) {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			int i = table.getSelectionModel().getSelectedIndex();
			try {
				String text = "Closed";
				r.setStatus(text);
				client.handleMessageFromClientUI("CHANGESTATUS," + r.getId() + "," + text);

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();

				return;
			}
			refreshTable();
			table.getSelectionModel().select(i);

		}
		if (choiceBox.getValue().equals("Frozen")) {
			Request r;
			r = table.getSelectionModel().getSelectedItem();
			int i = table.getSelectionModel().getSelectedIndex();
			try {
				String text = "Frozen";
				r.setStatus(text);
				client.handleMessageFromClientUI("CHANGESTATUS," + r.getId() + "," + text);

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setContentText(e2.getMessage() + "\nCouldn't save changes");
				alert.show();

				return;
			}
			refreshTable();
			table.getSelectionModel().select(i);
		}
	}
}
