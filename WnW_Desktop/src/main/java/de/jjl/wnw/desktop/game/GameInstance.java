package de.jjl.wnw.desktop.game;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.Config;
import de.jjl.wnw.base.rune.parser.Grid;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.game.GameObject;
import de.jjl.wnw.dev.rune.DesktopRune;
import de.jjl.wnw.dev.rune.DesktopRuneUtil;
import de.jjl.wnw.dev.spell.Spell;
import de.jjl.wnw.dev.spell.SpellUtil;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class GameInstance
{
	private static GameInstance instance;

	public static GameInstance getInstance()
	{
		if (instance == null)
		{
			instance = new GameInstance();
		}

		return instance;
	}

	private List<DesktopRune> currentRunes;

	private DesktopPlayer dummy;

	private long frameTime = 0;

	private long lastFrame = System.currentTimeMillis();

	private Collection<GameObject> objects;

	private WnWPathInputParser parser;

	private WnWDesktopPath path;

	private DesktopPlayer player;

	private boolean running;

	private GameInstance()
	{
		objects = new CopyOnWriteArrayList<>();
		currentRunes = new CopyOnWriteArrayList<>();
		parser = new WnWPathInputParser();
		player = new DesktopPlayer(0, 0);
		dummy = new DesktopPlayer(0, 0);
		running = true;

		dummy.faceLeft();
		objects.add(player);
		objects.add(dummy);
	}

	public void addPathPoint(int x, int y)
	{
		path.addPoint(x, y);
	}

	public boolean isRunning()
	{
		return running;
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

		DesktopRune dRune = DesktopRuneUtil.getRune(player, rune.getLong());

		if (dRune == null)
		{
			return;
		}

		dRune.setX(10 + currentRunes.size() * 40);
		dRune.setY(10);

		objects.add(dRune);

		currentRunes.add(dRune);
	}

	public void handleFrame(long now)
	{
		frameTime = now - lastFrame;
		lastFrame = now;

		moveObjects(frameTime);
		collide();
		refresh();
	}

	private void refresh()
	{
		if (dummy.getLives() <= 0)
		{
			running = false;
		}
	}

	private void collide()
	{
		for (GameObject obj1 : objects.stream().filter(go -> go instanceof DesktopPlayer).collect(Collectors.toList()))
		{
			for (GameObject obj2 : objects.stream().filter(go -> go instanceof Spell).collect(Collectors.toList()))
			{
				DesktopPlayer player = (DesktopPlayer) obj1;
				Spell spell = (Spell) obj2;

				if (spell.getCaster() != player && chkCollision(player, spell))
				{
					player.damage(spell.getDamage());
					spell.hit();
					objects.remove(spell);
				}
			}
		}
	}

	private boolean chkCollision(GameObject obj1, GameObject obj2)
	{
		if (obj1.getX() + obj1.getWidth() < obj2.getX() || obj2.getX() + obj2.getWidth() < obj1.getX()
				|| obj1.getY() + obj1.getHeight() < obj2.getY() || obj2.getY() + obj2.getHeight() < obj2.getHeight())
		{
			return false;
		}

		return true;
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

		return DesktopRuneUtil.getRune(player, reversedNumber);
	}

	private void moveObjects(long frameTime)
	{
		for (GameObject obj : objects)
		{
			if (!(obj instanceof DesktopPlayer) && !(obj instanceof DesktopRune))
			{
				obj.move(frameTime);
			}
		}
	}

	public void playerCast()
	{
		long[] combo = new long[currentRunes.size()];

		for (DesktopRune rune : currentRunes)
		{
			combo[currentRunes.indexOf(rune)] = rune.getLong();
		}

		Spell spell = SpellUtil.getSpell(player, combo);

		if (spell != null)
		{
			spell.setPos(player.getX(), player.getY());
			objects.add(spell);
			objects.add(spell);
		}

		currentRunes.forEach(dRune -> objects.remove(dRune));
		path = null;
		objects.removeAll(currentRunes);
		currentRunes.clear();
	}

	public void setDrawSize(double width, double height)
	{
		player.setX((int) (width / 100 * 10));
		player.setY((int) (height / 100 * 70));

		dummy.setX((int) (width / 100 * 90));
		dummy.setY((int) (height / 100 * 70));
	}

	public void startPath(WnWDisplaySystem display)
	{
		path = new WnWDesktopPath(display);
	}

	public Collection<GameObject> getObjects()
	{
		return objects;
	}

	public WnWDesktopPath getPath()
	{
		// TODO Auto-generated method stub
		return path;
	}

	public void drawDebug(GraphicsContext graphics)
	{
		graphics.setFont(new Font(10));
		graphics.fillText("GameObjects: " + objects.size(), 20, 60);
	}

}
