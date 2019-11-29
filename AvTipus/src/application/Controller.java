package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {

	public static Controller _ins;
	@FXML
	private Pane pane;

	@FXML
	private TableView<Request> table;

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
	private TextField addText;

	@FXML
	private Pane requestPane;

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
	private TextArea statusArea;

	@FXML
	private Button statusEditButton;

	@FXML
	private Button saveStatusButton;

	@FXML
	private Label idLabel;

	@FXML
	private TextField serachFeild;

	@FXML
	private Button SearchButton;

	public void initialize(){
		Controller._ins = this;
	}

	public TextArea getStatusArea() {
		return statusArea;
	}

	public void setStatusArea(TextArea statusArea) {
		this.statusArea = statusArea;
	}

	public Button getStatusEditButton() {
		return statusEditButton;
	}

	public void setStatusEditButton(Button statusEditButton) {
		this.statusEditButton = statusEditButton;
	}

	public Button getSaveStatusButton() {
		return saveStatusButton;
	}

	public void setSaveStatusButton(Button saveStatusButton) {
		this.saveStatusButton = saveStatusButton;
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
	public void showRequest(MouseEvent event) {
		Request r;
		try {
			r = table.getSelectionModel().getSelectedItem();
			getDescArea().setText(r.getDesc());
			getChangesArea().setText(r.getChange());
			getHandlerLabel().setText(r.getHandler());
			getStatusArea().setText(r.getStatus());
			getIdLabel().setText(Integer.toString(r.getId()));


			getChangesEditButton().setVisible(true);
			getDescEditButton().setVisible(true);
			getStatusEditButton().setVisible(true);
			getSaveDescButton().setVisible(false);
			getSaveChangesButton().setVisible(false);
			getSaveStatusButton().setVisible(false);
			getDescArea().setEditable(false);
			getChangesArea().setEditable(false);
			getStatusArea().setEditable(false);
		} catch (Exception e) {
			return;
		}

	}

	@FXML
	public void editDesc() {
		getDescArea().setEditable(true);
		getSaveDescButton().setVisible(true);
	}

	@FXML
	public void editChanges() {
		getChangesArea().setEditable(true);
		getSaveChangesButton().setVisible(true);
	}

	@FXML
	void editStatus() {
		getStatusArea().setEditable(true);
		getSaveStatusButton().setVisible(true);
	}

	@FXML
	public void addToTable()
	{
		try {
			DataBaseController.addToDB(gettAddText().getText().toString());
			refreshTable();
			gettAddText().setText("");
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong parameters!");
			alert.setContentText("Please enter 6 parameters:\n" + "string,INT,string,string,string,string\n"
					+ "The int is 0-5\n" + "The strings are up to 1000 characters");
			alert.show();
		}
	}

	@FXML
	public void saveChangesChanges()
	{
		Request r;
		r = table.getSelectionModel().getSelectedItem();
		int i = table.getSelectionModel().getSelectedIndex();
		try {
			String text = getChangesArea().getText();
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
	}

	@FXML
	public void saveDescChanges()
	{
		Request r;
		r = table.getSelectionModel().getSelectedItem();
		int i = table.getSelectionModel().getSelectedIndex();
		try {
			String text = getDescArea().getText();
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
		getDescArea().setEditable(false);
		getSaveDescButton().setVisible(false);
	}

	@FXML
	public void saveChangeStatus()
	{
		Request r;
		r = table.getSelectionModel().getSelectedItem();
		int i = table.getSelectionModel().getSelectedIndex();
		try {
			String text = getStatusArea().getText();
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
		getStatusArea().setEditable(false);
		getSaveStatusButton().setVisible(false);
	}

	public void refreshTable() {
		if (UserConsole._init.isSerach == false) {
			ObservableList<Request> ol = DataBaseController.getTable();
			table.setItems(ol);
		}
		if (UserConsole._init.isSerach) {
			ObservableList<Request> ol = DataBaseController.getTableWithID(UserConsole._init.searchid);
			table.setItems(ol);
		}
	}

	@FXML
	public void searchTable()
	{
		try {
			String text = getSerachFeild().getText();
			if (text.equals("*")) {
				UserConsole._init.isSerach = false;
				//refreshTable();
			} else {
				int id = Integer.parseUnsignedInt(text);
				UserConsole._init.isSerach = true;
				UserConsole._init.searchid = id;
				refreshTable();
			}
			getDescArea().setText("");
			getChangesArea().setText("");
			getHandlerLabel().setText("");
			getStatusArea().setText("");
			getIdLabel().setText("");

			getChangesEditButton().setVisible(false);
			getDescEditButton().setVisible(false);
			getStatusEditButton().setVisible(false);

			getSaveDescButton().setVisible(false);
			getSaveChangesButton().setVisible(false);
			getSaveStatusButton().setVisible(false);
			getDescArea().setEditable(false);
			getChangesArea().setEditable(false);
			getStatusArea().setEditable(false);
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
	}


}
