package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;
import java.util.Iterator;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.rune.parser.*;
import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.controls.PlayerController;
import de.jjl.wnw.desktop.game.*;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class PracticeDummyFrame extends Frame implements PlayerController, EventHandler<MouseEvent>
{
	private Canvas canvas;

	private String currentInput;

	private WnWPathInputParser parser;

	private WnWDesktopPath path;

	@FXML
	private BorderPane root;

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

		currentInput += currentInput.isEmpty() ? rune : "\\|" + rune;
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

	public void startPath(WnWDisplaySystem display)
	{
		path = new WnWDesktopPath(display);
	}

	@FXML
	private void initialize()
	{
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

		GameInstance.getInstance().setControllerPlayer1(this);

		canvas.widthProperty().addListener(
				(p, o, n) -> GameInstance.getInstance().setDrawSize(canvas.getWidth(), canvas.getHeight()));

		canvas.heightProperty().addListener(
				(p, o, n) -> GameInstance.getInstance().setDrawSize(canvas.getWidth(), canvas.getHeight()));

		pane.getChildren().add(canvas);

		root.setCenter(pane);

		root.sceneProperty().addListener((p, o, n) ->
		{
			if (n == null)
			{
				return;
			}

			n.setOnKeyPressed(e ->
			{
				if (e.getCode() == Settings.getCastKey())
				{
					path = null;
					finishPath();
					currentInput += currentInput.isEmpty() ? "A" : "|A";
				}

				if (e.getCode() == Settings.getShieldKey())
				{
					path = null;
					finishPath();
					currentInput += currentInput.isEmpty() ? "S" : "|S";
				}
			});
		});

		root.addEventFilter(MouseEvent.ANY, this);

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				GameInstance.getInstance().handleFrame(now);
				paintScene();

				if (!GameInstance.getInstance().isRunning())
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

		GameInstance.getInstance().getObjects().forEach(e -> e.drawOn(graphics));

		if (getPath() != null)
		{
			getPath().drawOn(graphics);
		}

		GameInstance.getInstance().drawDebug(graphics);
	}
}
