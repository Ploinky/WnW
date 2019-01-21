package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.desktop.game.*;
import de.jjl.wnw.desktop.gui.Frame;
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
	public PracticeDummyFrame(Game game)
	{
		super(game);
	}

	@FXML
	private BorderPane root;

	private Canvas canvas;

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
					GameInstance.getInstance().playerCast();
				}

				if (e.getCode() == Settings.getShieldKey())
				{
					GameInstance.getInstance().playerShield();
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
			GameInstance.getInstance().addPathPoint((int) e.getX(), (int) e.getY());
		});

		root.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
		{
			GameInstance.getInstance().finishPath();
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
}
