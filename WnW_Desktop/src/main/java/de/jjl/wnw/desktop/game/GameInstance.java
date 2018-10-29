package de.jjl.wnw.desktop.game;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.*;
import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.game.GameObject;
import de.jjl.wnw.dev.rune.*;
import de.jjl.wnw.dev.spell.*;

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

	private GameInstance()
	{
		objects = new CopyOnWriteArrayList<>();
		currentRunes = new CopyOnWriteArrayList<>();
		parser = new WnWPathInputParser();
		player = new DesktopPlayer(0, 0);
		dummy = new DesktopPlayer(0, 0);

		dummy.faceLeft();
		objects.add(player);
		objects.add(dummy);
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

		DesktopRune dRune = DesktopRuneUtil.getRune(rune.getLong());

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

		// TODO $Li 05.09.2018 For debugging only
		System.out.println("GameObjects: " + objects.size());
	}

	private void collide()
	{
		for (GameObject obj1 : objects.stream().filter(go -> go instanceof DesktopPlayer).collect(Collectors.toList()))
		{
			for (GameObject obj2 : objects.stream().filter(go -> go instanceof DesktopRune)
					.collect(Collectors.toList()))
			{
				if (obj1 == obj2)
				{
					continue;
				}
			}
		}
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

	private void moveObjects(long frameTime)
	{
		for (GameObject obj : objects)
		{
			obj.move(frameTime);
		}
	}

	public void playerCast()
	{
		long[] combo = new long[currentRunes.size()];

		for (DesktopRune rune : currentRunes)
		{
			combo[currentRunes.indexOf(rune)] = rune.getLong();
		}

		Spell spell = SpellUtil.getSpell(combo);

		if (spell != null)
		{
			spell.setPos(player.getX(), player.getY());
			objects.add(spell);
			objects.add(spell);
		}

		currentRunes.forEach(dRune -> objects.remove(dRune));
		path = null;
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

}
