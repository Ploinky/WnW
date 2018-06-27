package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.Config;
import de.jjl.wnw.base.rune.parser.Grid;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.desktop.game.DesktopPlayer;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Drawable;
import de.jjl.wnw.desktop.gui.Frame;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.rune.DesktopRune;
import de.jjl.wnw.dev.rune.DesktopRuneUtil;
import de.jjl.wnw.dev.spell.Spell;
import de.jjl.wnw.dev.spell.SpellUtil;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
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

	private List<DesktopRune> currentRunes;

	@FXML
	private void initialize()
	{
		drawObjects = new CopyOnWriteArrayList<>();
		currentRunes = new CopyOnWriteArrayList<>();
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
			if (e.getButton() == MouseButton.SECONDARY)
			{
				long[] combo = new long[currentRunes.size()];

				for (DesktopRune rune : currentRunes)
				{
					combo[currentRunes.indexOf(rune)] = rune.getLong();
				}

				Spell spell = SpellUtil.getRune(combo);

				// TODO $Li 27.06.2018 For debugging only
				System.out.println(spell);

				currentRunes.forEach(dRune -> drawObjects.remove(dRune));
				path = null;
				currentRunes.clear();
				return;
			}

			if (e.getButton() != MouseButton.PRIMARY)
			{
				return;
			}

			path = new WnWDesktopPath(
					new WnWDisplaySystem((int) root.getWidth(), (int) root.getHeight(), false, false, 0, 0));
		});

		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, e ->
		{
			if (e.getButton() != MouseButton.PRIMARY)
			{
				return;
			}

			path.addPoint((int) e.getX(), (int) e.getY());
		});

		root.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
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

			DesktopRune dRune = DesktopRuneUtil.getRune(rune.getLong());

			if (dRune == null)
			{
				return;
			}

			dRune.setX(10 + currentRunes.size() * 40);
			dRune.setY(10);

			drawObjects.add(dRune);

			currentRunes.add(dRune);
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

	private WnWRune lookupRune(WnWPath path, Config config)
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

		return DesktopRuneUtil.getRune(reversedNumber);
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
