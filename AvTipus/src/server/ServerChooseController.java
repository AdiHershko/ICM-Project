package server;

import client.ChatClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

public class ServerChooseController {

	@FXML
	private TextField hostfield;
	@FXML
	private Pane serverPane;

	@FXML
	private TextField portfield;

	@FXML
	private TextField dbfield;

	@FXML
	private TextField unfield;

	@FXML
	private TextField passfield;
	@FXML
	private  TextField S_portField;

	public TextField getS_portField() {
		return S_portField;
	}

	public void setS_portField(TextField s_portField) {
		S_portField = s_portField;
	}

	@FXML
	private Button connectbtn;
	@FXML
	private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

	public Pane getServerPane() {
		return serverPane;
	}

	@FXML
	void connect(ActionEvent event) {

		if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
			String temp = "jdbc:mysql://";
			temp += hostfield.getText();
			temp += ":";
			temp += portfield.getText();
			temp += "/";
			temp += dbfield.getText();
			temp += "?useLegacyDatetimeCode=false&serverTimezone=UTC";
			DataBaseController.setUrl(temp);
			DataBaseController.setPassword(passfield.getText());
			DataBaseController.setUsername(unfield.getText());
		}
		
		int temp = EchoServer.Start(Integer.valueOf(getS_portField().getText()));
		if (temp == 1) {
			ServerConsole.stage.close();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("connection to database failed");
			alert.show();

		}
		else if (temp == 2) {
			ServerConsole.stage.close();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("can't listen to client");
			alert.show();
		}
		else {
			ChatClient.DEFAULT_PORT=Integer.valueOf(getS_portField().getText());
			ServerConsole.stage.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successful");
			alert.setContentText("system connected!");
			alert.show();
		}

	}

	public TextField getHostfield() {
		return hostfield;
	}

	public void setHostfield(TextField hostfield) {
		this.hostfield = hostfield;
	}

	public TextField getPortfield() {
		return portfield;
	}

	public void setPortfield(TextField portfield) {
		this.portfield = portfield;
	}

	public TextField getDbfield() {
		return dbfield;
	}

	public void setDbfield(TextField dbfield) {
		this.dbfield = dbfield;
	}

	public TextField getUnfield() {
		return unfield;
	}

	public void setUnfield(TextField unfield) {
		this.unfield = unfield;
	}

	public TextField getPassfield() {
		return passfield;
	}

	public void setPassfield(TextField passfield) {
		this.passfield = passfield;
	}

	public Button getConnectbtn() {
		return connectbtn;
	}

	public void setConnectbtn(Button connectbtn) {
		this.connectbtn = connectbtn;
	}

	public ChoiceBox<String> getChoiceBox() {
		return choiceBox;
	}

	public void setServerPane(Pane serverPane) {
		this.serverPane = serverPane;
	}

	public void onBoxChange() {
		if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
			serverPane.setVisible(true);
		}
	}

	public void setChoiceBox() {
		choiceBox.getItems().add("Our Remote SQL Database");
		choiceBox.getItems().add("Local SQL Server, needs to have scheme and tables for ICM");
		choiceBox.setValue("Our Remote SQL Database");
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (choiceBox.getValue().equals("Local SQL Server, needs to have scheme and tables for ICM")) {
					serverPane.setVisible(false);
				}
				else {
					serverPane.setVisible(true);
				}
			}

		});

	}

}
