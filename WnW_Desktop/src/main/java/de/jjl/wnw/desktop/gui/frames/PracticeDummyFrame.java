package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.*;
import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.game.*;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.rune.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class PracticeDummyFrame extends Frame
{
	private Canvas canvas;

	private String currentInput;

	private WnWPathInputParser parser;

	private WnWDesktopPath path;

	public WnWDesktopPath getPath()
	{
		// TODO Auto-generated method stub
		return path;
	}

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
		WnWRune rune = lookupRune(filteredPath, new Config());

		if (rune == null)
		{
			return;
		}

		DesktopRune dRune = DesktopRuneUtil.getRune(player1, rune.getLong());

		if (dRune == null)
		{
			return;
		}

		dRune.setX(10 + (currentInput.split("\\|").length + 1) * 40);
		dRune.setY(10);

		currentInput += currentInput.isEmpty() ? dRune.getLong() : "\\|" + dRune.getLong();
	}

	public void startPath(WnWDisplaySystem display)
	{
		path = new WnWDesktopPath(display);
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
					currentInput += currentInput.isEmpty() ? "A" : "\\|A";
				}

				if (e.getCode() == Settings.getShieldKey())
				{
					currentInput += currentInput.isEmpty() ? "S" : "\\|S";
				}
			});
		});

		root.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
		{
			GameInstance.getInstance()
					.startPath(new WnWDisplaySystem((int) root.getWidth(), (int) root.getHeight(), false, false, 0, 0));
		});

		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, e ->
		{
			addPathPoint((int) e.getX(), (int) e.getY());
		});

		root.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
		{
			finishPath();
		});

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

	private void paintScene()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		GameInstance.getInstance().getObjects().forEach(e -> e.drawOn(graphics));

		if (GameInstance.getInstance().getPath() != null)
		{
			GameInstance.getInstance().getPath().drawOn(graphics);
		}

		GameInstance.getInstance().drawDebug(graphics);
	}
}
