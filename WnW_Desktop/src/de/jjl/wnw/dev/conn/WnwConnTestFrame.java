package de.jjl.wnw.dev.conn;

import java.io.IOException;
import java.util.Collections;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WnwConnTestFrame extends Application
{
	private static final int PORT = 1234;
	
	public static final String MSGTYPE_MSG = "Msg";
	public static final String MSGTYPE_WAIT = "Wait";
	public static final String MSGTYPE_WAITRESPONSE = "Response";
	public static final String MSGPARAM_MSG = "Text";
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@FXML
	private ListView<WnWConnection> lstConn;
	
	@FXML
	private ListView<WnWMsg> lstMsg;
	
	@FXML
	private Button btnServer;
	
	@FXML
	private Button btnConn;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private BorderPane borderConn;
	
	@FXML
	private ComboBox<WnWConnection> cmbConn;
	
	@FXML
	private Button btnSend;
	
	@FXML
	private Button btnSendWait;
	
	@FXML
	private TextArea txtMsg;
	
	@FXML
	private ListView<WnWMsg> lstMsgsConn;
	
	private ObjectProperty<WnWServer> server;
	private ObservableList<WnWConnection> serverConnections;
	private ObservableList<WnWConnection> connections;
	private ObservableList<WnWMsg> msgs;
	private FilteredList<WnWMsg> msgFiltered;
	
	public WnwConnTestFrame()
	{
		server = new SimpleObjectProperty<WnWServer>(this, "server", null);
		serverConnections = FXCollections.observableArrayList();
		connections = FXCollections.observableArrayList();
		msgs = FXCollections.observableArrayList();
		msgFiltered = msgs.filtered(msg -> true);
	}

	@Override
	public void start(Stage mainStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Frame.fxml"));
		
		loader.setController(this);
		
		Scene scene = new Scene(loader.load());
		
		mainStage.setScene(scene);
		
		init0();
		
		mainStage.show();
	}
	
	private void init0()
	{
		btnServer.disableProperty().bind(server.isNotNull());
		
		lstConn.setItems(connections);
		lstConn.setCellFactory(
			lst -> new ListCell<WnWConnection>()
				{
					@Override
					protected void updateItem(WnWConnection item, boolean empty)
					{
						super.updateItem(item, empty);
						
						setText(empty ? null : item.getName());
					}
				});
		
		cmbConn.setItems(connections);
		cmbConn.setCellFactory(
			lst -> new ListCell<WnWConnection>()
				{
					@Override
					protected void updateItem(WnWConnection item, boolean empty)
					{
						super.updateItem(item, empty);
						
						setText(empty ? null : item.getName());
					}
				});
		
		btnConn.disableProperty().bind(
			Bindings.createBooleanBinding(
				() -> txtName.getText().isEmpty() == false
						&& connections.stream()
										.map(WnWConnection::getName)
										.filter(name -> name.equals(txtName.getText()))
										.findAny().isPresent(),
				txtName.textProperty(),
				connections));
		
		lstMsg.setItems(msgs);
		lstMsg.setCellFactory(
			lst -> new ListCell<WnWMsg>()
				{
					@Override
					protected void updateItem(WnWMsg item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if(empty)
						{
							setText(null);
							return;
						}
						
						if(item.getType().equals(MSGTYPE_WAITRESPONSE))
						{
							setText("Send<" + item.buildMsgString() + ">");
						}
						else
						{
							setText("Received<" + item.buildMsgString() + ">");
						}
					}
				});
	}
	
	@FXML
	private void startServer()
	{
		if(server.get() != null)
		{
			return;
		}
		
		try
		{
			WnWServer s = new WnWServer(PORT);
			s.start();
			s.setConnectionHandler(conn ->
				{
					serverConnections.add(conn);
					conn.addMsgListener((c, msg) -> msgs.add(msg));
				});
			server.set(s);
		}
		catch (IOException e)
		{
			new Alert(AlertType.ERROR,
					"Could not open server due to <" + e.getClass().getSimpleName() + "-" + e.getMessage() + ">",
					ButtonType.OK, ButtonType.CLOSE)
				.show();
		}
	}
	
	@FXML
	private void openConnection()
	{
		try
		{
			WnWConnection conn = new WnWConnection("localhost", PORT);
			conn.setName(txtName.getText());
			connections.add(conn);
		}
		catch (IOException e)
		{
			new Alert(AlertType.ERROR,
					"Could no create connection due to <" + e.getClass().getSimpleName() + "-" + e.getMessage() + ">",
					ButtonType.OK, ButtonType.CLOSE)
				.show();
		}
	}
	
	@FXML
	private void sendMsg()
	{
		cmbConn.getSelectionModel().getSelectedItem().sendMsg(
				new WnWMsg(MSGTYPE_MSG, Collections.singletonMap(MSGPARAM_MSG, txtMsg.getText())));
	}
	
	@FXML
	private void sendMsgWait()
	{
		cmbConn.getSelectionModel().getSelectedItem().sendMsg(
				new WnWMsg(MSGTYPE_WAIT, Collections.singletonMap(MSGPARAM_MSG, txtMsg.getText())));
	}
}
