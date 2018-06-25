package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import de.jjl.wnw.base.rune.parser.Config;
import de.jjl.wnw.base.rune.parser.Grid;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.desktop.game.DesktopPlayer;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Drawable;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PracticeDummyFrame extends Frame
{
	public PracticeDummyFrame(Game game)
	{
		super(game);
	}
	
	private long lastFrame = System.currentTimeMillis();
	
	private long frameTime = 0;

	private Collection<Drawable> drawObjects;
	
	@FXML
	private BorderPane root;

	private Canvas canvas;

	private WnWPathInputParser parser;

	private WnWDesktopPath path;

	private boolean isDrawing;
	
	@FXML
	private void initialize()
	{
		drawObjects = new ArrayList<>();
		isDrawing = false;
		canvas = new Canvas();
		
		Pane pane = new Pane()
		{
			@Override
			protected void layoutChildren()
			{
				super.layoutChildren();
				final double x = snappedLeftInset();
				final double y = snappedTopInset();
				// Java 9 - snapSize is depricated used snapSizeX() and snapSizeY() accordingly
				final double w = snapSize(getWidth()) - x - snappedRightInset();
				final double h = snapSize(getHeight()) - y - snappedBottomInset();
				canvas.setLayoutX(x);
				canvas.setLayoutY(y);
				canvas.setWidth(w);
				canvas.setHeight(h);
			}
		};
		
		DesktopPlayer player = new DesktopPlayer(0, 0); 
		drawObjects.add(player);
		
		canvas.widthProperty().addListener((p, o, n) ->
		{
			player.setX((int) (canvas.getWidth() / 100 * 10));
			player.setY((int) (canvas.getHeight() / 100 * 70));
		});
		canvas.heightProperty().addListener((p, o, n) ->
		{
			player.setX((int) (canvas.getWidth() / 100 * 10));
			player.setY((int) (canvas.getHeight() / 100 * 70));
		});
		
		pane.getChildren().add(canvas);
		
		root.setCenter(pane);
		parser = new WnWPathInputParser();

		root.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
		{
			path = new WnWDesktopPath(
					new WnWDisplaySystem((int) root.getWidth(), (int) root.getHeight(), false, false, 0, 0));
			isDrawing = true;
		});

		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, e ->
		{
			path.addPoint((int) e.getX(), (int) e.getY());
		});

		root.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
		{
			if (path == null)
			{
				return;
			}

			isDrawing = false;
			WnWPath wnwPath = path.trimmed();
			Grid grid = parser.buildGrid(wnwPath, new Config());
			WnWPath filteredPath = new WnWPathInputParser().filterRunePath(wnwPath, new Config(), grid);
			System.out.println(parser.lookupRune(filteredPath, new Config()));
		});
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				frameTime = now - lastFrame;
				lastFrame = now;
				
				paintScene(frameTime);
			}
		};
		timer.start();
	}

	private void paintScene(long frameTime)
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		drawObjects.forEach(e -> e.drawOn(graphics, frameTime));
		
		if (path != null)
		{
			path.drawOn(graphics, frameTime);
		}
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
