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
	private Button button;
	@FXML
	private TableView<Request> table;
	@FXML
	private Button addButton;
	@FXML
	private TextField addText;
	@FXML
	private TextField changeText;
	@FXML
	private Button changeButton;
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


	public Button getDescEditButton()
	{
		return descEditButton;
	}

	public Button getChangesEditButton()
	{
		return changesEditButton;
	}

	public Button getSaveDescButton()
	{
		return saveDescButton;
	}

	public Button getSaveChangesButton()
	{
		return saveChangesButton;
	}

	public Label getHandlerLabel()
	{
		return handlerLabel;
	}
	public TextArea getChangesArea()
	{
		return changesArea;
	}
	public TextArea getDescArea()
	{
		return descArea;
	}

	public Pane getRequestPane()
	{
		return requestPane;
	}



	public TextField getChangeText()
	{
		return changeText;
	}

	public Button getChangeButton()
	{
		return changeButton;
	}


	public TextField gettAddText()
	{
		return addText;
	}

	public Button getAddButton()
	{
		return addButton;
	}

	public TableView<?> getTable()
	{
		return table;
	}




	public Button getButton()
	{
		return button;
	}

	@FXML
    public void showRequest(MouseEvent event)
    {
		Request r;
    	try {
    		r = table.getSelectionModel().getSelectedItem();
    	}
    	catch (Exception e)
    	{
    		return;
    	}
    	getDescArea().setText(r.getDesc());
    	getChangesArea().setText(r.getChange());
    	getHandlerLabel().setText(r.getHandler());
    	getChangeText().setText("");
    }

	@FXML
	public void editDesc()
	{
		getDescArea().setEditable(true);
		getSaveDescButton().setVisible(true);
	}

	@FXML
	public void editChanges()
	{
		getChangesArea().setEditable(true);
		getSaveChangesButton().setVisible(true);
	}



}
