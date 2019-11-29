package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {

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

}
