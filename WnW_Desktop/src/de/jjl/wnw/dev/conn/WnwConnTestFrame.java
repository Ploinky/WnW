package de.jjl.wnw.dev.conn;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WnwConnTestFrame extends Application
{
	private static final int PORT = 1234;
	
	public static final String MSGTYPE_MSG = "Msg";
	public static final String MSGTYPE_WAIT = "Wait";
	public static final String MSGTYPE_WAITRESPONSE = "Response";
	public static final String MSGTYPE_NAME = "Name";
	public static final String MSGPARAM_MSG = "Text";
	public static final String MSGPARAM_NAME = "Name";
	public static final String MSGPARAM_TIME = "Time";
	public static final String MSGPARAM_ID = "Id";
	
	public static final long WAIT_TIMEOUT = 5 * 1000;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@FXML
	private Pane rootConn;
	@FXML
	private Button btnServer;
	@FXML
	private Button btnCreConn;
	@FXML
	private ComboBox<WnWConnTestConnection> cmbConn;
	@FXML
	private TextField txtName;
	@FXML
	private Button btnSend;
	@FXML
	private Button btnSendWait;
	@FXML
	private TextArea txtMsg;
	@FXML
	private ListView<WnWMsg> lstMsgsConn;
	
	@FXML
	private ListView<WnWConnTestConnection> lstConn;
	@FXML
	private ListView<WnwConnTestMsg> lstMsg;
	
	private WnWConnTestServer server;
	private ObservableList<WnWConnTestConnection> connections;
	
	public WnwConnTestFrame()
	{
		server = new WnWConnTestServer(PORT);
		connections = FXCollections.observableArrayList();
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
		
		mainStage.setOnHidden(e -> System.exit(0));
	}
	
	private void init0()
	{
		btnServer.textProperty().bind(
			Bindings.when(server.runningProperty())
				.then("Stop Server")
				.otherwise("Start Server"));
		
		btnCreConn.disableProperty().bind(
			server.runningProperty().not().or(
				Bindings.createBooleanBinding(
					() ->
						{
							String name = txtName.getText();
							if(name.isEmpty())
							{
								return true;
							}
							for(WnWConnTestConnection conn : connections)
							{
								if(conn.getName().equals(name))
								{
									return true;
								}
							}
							return false;
						},
					txtName.textProperty(),
					connections)));
		
		lstConn.setItems(server.getConnections());
		lstConn.setCellFactory(
			lst -> new ListCell<WnWConnTestConnection>()
				{
					@Override
					protected void updateItem(WnWConnTestConnection item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if(empty)
						{
							textProperty().unbind();
							setText(null);
						}
						else
						{
							textProperty().unbind();
							textProperty().bind(item.nameProperty());
						}
					}
				});
		
		lstMsg.setItems(server.getMsgs());
		lstMsg.setCellFactory(
				lst -> new ListCell<WnwConnTestMsg>()
				{
					@Override
					protected void updateItem(WnwConnTestMsg item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if(empty)
						{
							setText(null);
						}
						else
						{
							setText(item.getConn().getName() + ": "
									+ (item.wasSend() ? "Sent " : "Received ")
									+ item.getMsg());
						}
					}
				});
		
		cmbConn.setItems(connections);
		cmbConn.setCellFactory(
			lst -> new ListCell<WnWConnTestConnection>()
				{
					@Override
					protected void updateItem(WnWConnTestConnection item, boolean empty)
					{
						super.updateItem(item, empty);
						
						setText(empty ? null : item.getName());
					}
				});
		cmbConn.setButtonCell(new ListCell<WnWConnTestConnection>()
				{
					@Override
					protected void updateItem(WnWConnTestConnection item, boolean empty)
					{
						super.updateItem(item, empty);
						
						setText(empty ? null : item.getName());
					}
				});
		
		btnSend.disableProperty().bind(
				server.runningProperty().not()
					.or(cmbConn.getSelectionModel().selectedItemProperty().isNull()));
		btnSendWait.disableProperty().bind(
				server.runningProperty().not()
				.or(cmbConn.getSelectionModel().selectedItemProperty().isNull()));
		
		lstMsgsConn.itemsProperty().bind(
				Bindings.createObjectBinding(
						() ->
							{
								WnWConnTestConnection conn = cmbConn.getSelectionModel().getSelectedItem();
								if(conn == null)
								{
									return FXCollections.observableArrayList();
								}
								return conn.getMsgs();
							},
						cmbConn.getSelectionModel().selectedItemProperty()));
	}
	
	@FXML
	private void startServer()
	{
		if(server.isRunning())
		{
			server.stop();
		}
		else
		{
			try
			{
				server.start();
			}
			catch (IOException e)
			{
				new Alert(AlertType.ERROR,
						"Could not open server due to <" + e.getClass().getSimpleName() + "-" + e.getMessage() + ">",
						ButtonType.OK, ButtonType.CLOSE)
				.show();
			}
		}
	}
	
	@FXML
	private void openConnection()
	{
		try
		{
			WnWConnection conn = new WnWConnection("localhost", PORT);
			WnWConnTestConnection c = new WnWConnTestConnection(conn);
			c.setName(txtName.getText());
			connections.add(c);
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
		cmbConn.getSelectionModel().getSelectedItem().sendMsg(txtMsg.getText());
	}
	
	@FXML
	private void sendWait()
	{
		Stage stage = new Stage(StageStyle.UNDECORATED);
		stage.initModality(Modality.APPLICATION_MODAL);
		Label msg = new Label();
		ProgressIndicator p = new ProgressIndicator();
		Button b = new Button("close");
		VBox v = new VBox(msg, p, b);
		stage.setScene(new Scene(v));
		
		WaitTask task = new WaitTask();
		
//		p.progressProperty().bind(task.progressProperty());
		msg.textProperty().bind(task.messageProperty());
		b.setOnAction(e -> stage.close());
		b.setDisable(true);
		
		task.stateProperty().addListener((pp, o, n) ->
			{
				switch(n)
				{
					case CANCELLED:
					case FAILED:
					case SUCCEEDED:
					{
						b.setDisable(false);
						break;
					}
					case RUNNING:
					{
						stage.show();
						break;
					}
					case READY:
					case SCHEDULED:
					{
						break;
					}
				}
			});
		
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
	
	private class WaitTask extends Task<Void>
	{

		@Override
		protected Void call() throws Exception
		{
			long timeout = Math.round(
					Math.random() * WAIT_TIMEOUT + 2);
			
			updateMessage("Sending Wait with timeout<" + timeout + ">");
			updateProgress(1, 2);
			
			cmbConn.getSelectionModel().getSelectedItem().sendWait(
				txtMsg.getText(),
				timeout);
			
			return null;
		}
		
		@Override
		protected void succeeded()
		{
			updateMessage("Wait Successfull");
			updateProgress(2, 2);
		}
		
		@Override
		protected void failed()
		{
			updateMessage("Wait Failed");
			updateProgress(2, 2);
		}
	}
}
