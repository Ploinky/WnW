package de.jjl.wnw.desktop.gui.frames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Iterator;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.msg.MsgPlayerInput;
import de.jjl.wnw.base.rune.parser.Config;
import de.jjl.wnw.base.rune.parser.Grid;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.desktop.game.Chat;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.PlayerController;
import de.jjl.wnw.dev.game.ClientGameInstance;
import de.jjl.wnw.dev.log.Debug;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PracticeDummyFrame extends Frame implements PlayerController, EventHandler<MouseEvent>
{
	private Canvas canvas;

	private String currentInput;

	private DesktopObjectPainter painter;

	private WnWPathInputParser parser;
	
	private WnWDesktopPath path;

	private BufferedReader reader;
	
	@FXML
	private BorderPane root;
	
	private Socket server;

	private BufferedWriter writer;
	
	public PracticeDummyFrame(Game game)
	{
		super(game);
		currentInput = "";
		parser = new WnWPathInputParser();
	}

	public void addPathPoint(int x, int y)
	{
		path.addPoint(x, y);
	}

	public void finishPath()
	{
		if (path == null)
		{
			return;
		}

		WnWPath wnwPath = path.trimmed();

		Grid grid = parser.buildGrid(wnwPath, new Config());
		WnWPath filteredPath = new WnWPathInputParser().filterRunePath(wnwPath, new Config(), grid);

		long rune = lookupRuneLong(filteredPath, new Config());

		currentInput += currentInput.isEmpty() ? rune : "|" + rune;
		System.out.println("Player input: " + currentInput);
	}

	@Override
	public Parent getAsNode()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);

		try
		{
			return loader.load(getClass().getResourceAsStream("/xml/PRACTICEDUMMY.fxml"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String getInputString()
	{
		String input = currentInput;

		currentInput = "";

		return input;
	}

	public WnWDesktopPath getPath()
	{
		return path;
	}

	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_RELEASED)
		{
			finishPath();
		}

		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
		{
			addPathPoint((int) event.getX(), (int) event.getY());
		}

		if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
		{
			startPath(new WnWDisplaySystem((int) root.getWidth(), (int) root.getHeight(), false, false, 0, 0));
		}
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void sendMsg(MsgChatMessage msg)
	{
		// TODO Auto-generated method stub
		
	}

	public void startPath(WnWDisplaySystem display)
	{
		path = new WnWDesktopPath(display);
	}

	@Override
	public void updateGameState(String state)
	{
		ClientGameInstance.getInstance().updateState(state);
	}

	private void handleChatMessage(WnWMap msgMap)
	{
		MsgChatMessage msg = new MsgChatMessage();
		msg.fromMap(msgMap);
		
		ClientGameInstance.getInstance().getObjects().stream()
			.filter(Chat.class::isInstance)
			.map(Chat.class::cast)
			.forEach(chat ->
			{
				chat.addChatMessage(msg);
				chat.show();
			});
	}

	private void handleGameMessage(WnWMap msgMap)
	{
		switch(msgMap.get(MsgConst.TYPE))
		{
			case MsgGameState.TYPE:
				updateGameState(msgMap.toString());
				break;
			case MsgChatMessage.TYPE:
				handleChatMessage(msgMap);
				break;
				default:
					System.out.println("Unknown message type <" + msgMap.get(MsgConst.TYPE) + ">");
		}
		
	}

	@FXML
	private void initialize()
	{
		try
		{
			Debug.log("Attempting to connect to server at localhost.");
			// TODO $Li 26.02.2019 close this
			server = new Socket("localhost", 50002);
			server.setSoTimeout(0);
			
			Debug.log("Connected to server at localhost.");
			
			reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		canvas = new Canvas();

		Pane pane = new Pane()
		{
			@Override
			protected void layoutChildren()
			{
				super.layoutChildren();
				final double x = snappedLeftInset();
				final double y = snappedTopInset();
				// Java 9 - snapSize is deprecated used snapSizeX() and snapSizeY() accordingly
				final double w = snapSize(getWidth()) - x - snappedRightInset();
				final double h = snapSize(getHeight()) - y - snappedBottomInset();
				canvas.setLayoutX(x);
				canvas.setLayoutY(y);
				canvas.setWidth(w);
				canvas.setHeight(h);
			}
		};

		painter = new DesktopObjectPainter(canvas.getGraphicsContext2D());

		pane.getChildren().add(canvas);
		
		Chat chat = new Chat();
		ClientGameInstance.getInstance().getObjects().add(chat);

		root.setCenter(pane);

		root.sceneProperty().addListener((p, o, n) ->
		{
			if (n == null)
			{
				return;
			}

			n.setOnKeyPressed(e ->
			{
			});
		});

		root.addEventFilter(MouseEvent.ANY, this);
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update();
				paintScene();

				if (!ClientGameInstance.getInstance().isRunning())
				{

					stop();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Game over");
					alert.setHeaderText("Player 1 wins!!! Great, you beat a dummy...");
					alert.setOnCloseRequest(e -> Platform.exit());
					alert.show();
				}
			}
		};

		timer.start();
		
		pane.sceneProperty().addListener((p, o, n) ->
		{
			if(n == null)
			{
				return;
			}
			
			n.addEventFilter(KeyEvent.KEY_PRESSED, e ->
			{
				if (e.getCode().equals(KeyCode.ENTER))
				{
					if(!chat.isEnabled())
					{
						chat.setEnabled(true);
					}
					else
					{
						chat.setEnabled(false);
						sendChatMessage(chat.getInput());
						chat.clear();
					}
					return;
				}
				
				if (e.getCode().equals(KeyCode.BACK_SPACE))
				{
					if(chat.isEnabled())
					{
						chat.delChar();
					}
					return;
				}
				
				if(chat.isEnabled())
				{
					chat.addInput(e.isShiftDown() ? e.getText().toUpperCase() : e.getText());
					return;
				}
				
				if (e.getCode().equals(Settings.getCastKey()))
				{
					path = null;
					finishPath();
					currentInput += currentInput.isEmpty() ? "A" : "|A";
				}

				if (e.getCode().equals(Settings.getShieldKey()))
				{
					path = null;
					finishPath();
					currentInput += currentInput.isEmpty() ? "S" : "|S";
				}
				
			});
		});
	}

	private long lookupRuneLong(WnWPath path, Config config)
	{
		long runeLong = 0;

		long i = 1;

		Iterator<WnWPoint> it = path.iterator();

		while (it.hasNext())
		{
			WnWPoint point = it.next();

			runeLong += ((config.getGridHeight() * point.getY() + (point.getX() + 1)) * i);

			i *= 10;
		}

		long reversedNumber = 0;

		while (runeLong > 0)
		{
			long temp = runeLong % 10;
			reversedNumber = reversedNumber * 10 + temp;
			runeLong = runeLong / 10;
		}

		return reversedNumber;
	}

	private void paintScene()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		ClientGameInstance.getInstance().getObjects().forEach(e -> painter.draw(e));

		if (getPath() != null)
		{
			getPath().drawOn(graphics);
		}
	}
	
	private void sendChatMessage(String text)
	{
		MsgChatMessage msg = new MsgChatMessage();
		msg.setPlayer("" + Thread.currentThread().getId());
		msg.setTimeStamp("[" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond() + "]");
		msg.setMsg(text);
		
		try
		{
			writer.write(msg.getMsgMap().toString() + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendInput()
	{
		if(currentInput == null || currentInput.isBlank())
		{
			return;
		}
		
		MsgPlayerInput msg = new MsgPlayerInput();
		msg.setInput(currentInput);
		currentInput = "";
		
		try
		{
			writer.write(msg.getMsgMap().toString() + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void update()
	{
		if(ClientGameInstance.getInstance().isRunning())
		{
			try
			{
				sendInput();
				
				String s = reader.readLine();
				
				if(s == null || s.isEmpty())
				{
					return;
				}
				
				WnWMap msgMap = new WnWMap(s);
				
				if(msgMap.containsKey(MsgConst.TYPE) && msgMap.get(MsgConst.TYPE) != null)
				{
					handleGameMessage(msgMap);
				}
			}
			catch(SocketException sock)
			{
				System.err.println("Lost connection to server at <" + server.getInetAddress() + ">");
				ClientGameInstance.getInstance().stop();
				return;
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}
}
