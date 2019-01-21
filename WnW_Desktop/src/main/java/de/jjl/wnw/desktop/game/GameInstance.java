package de.jjl.wnw.desktop.game;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.rune.parser.*;
import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import de.jjl.wnw.dev.game.GameObject;
import de.jjl.wnw.dev.rune.*;
import de.jjl.wnw.dev.spell.*;
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

	private Collection<GameObject> removeObjects;

	private WnWPathInputParser parser;

	private WnWDesktopPath path;

	private DesktopPlayer player;

	private boolean running;

	private GameInstance()
	{
		objects = new CopyOnWriteArrayList<>();
		removeObjects = new CopyOnWriteArrayList<>();
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

	public void drawDebug(GraphicsContext graphics)
	{
		graphics.setFont(new Font(10));
		graphics.fillText("GameObjects: " + objects.size(), 20, 60);
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

	public Collection<GameObject> getObjects()
	{
		return objects;
	}

	public WnWDesktopPath getPath()
	{
		// TODO Auto-generated method stub
		return path;
	}

	public void handleFrame(long now)
	{
		frameTime = now - lastFrame;
		lastFrame = now;

		moveObjects(frameTime);
		collide();
		refresh();
	}

	public boolean isRunning()
	{
		return running;
	}

	public void playerCast()
	{
		long[] combo = new long[currentRunes.size()];

		for (DesktopRune rune : currentRunes)
		{
			combo[currentRunes.indexOf(rune)] = rune.getLong();
		}

		Spell spell = SpellUtil.getSpell(player, combo, false);

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

	public void playerShield()
	{
		long[] combo = new long[currentRunes.size()];

		for (DesktopRune rune : currentRunes)
		{
			combo[currentRunes.indexOf(rune)] = rune.getLong();
		}

		Spell spell = SpellUtil.getSpell(player, combo, true);
		Spell dummySpell = SpellUtil.getSpell(dummy, combo, true);

		if (spell != null)
		{
			spell.setPos(player.getX(), player.getY());
			objects.add(spell);
			objects.add(spell);

			dummySpell.setPos(dummy.getX(), dummy.getY());
			objects.add(dummySpell);
			objects.add(dummySpell);
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

	private boolean chkCollision(GameObject obj1, GameObject obj2)
	{
		if (obj1.getX() + obj1.getWidth() < obj2.getX() || obj2.getX() + obj2.getWidth() < obj1.getX()
				|| obj1.getY() + obj1.getHeight() < obj2.getY() || obj2.getY() + obj2.getHeight() < obj1.getY())
		{
			return false;
		}

		return true;
	}

	private void collide()
	{
		Map<GameObject, List<GameObject>> collisions = new HashMap<>();

		for (GameObject obj1 : objects)
		{
			for (GameObject obj2 : objects)
			{
				if (obj1 == obj2)
				{
					continue;
				}

				if (collisions.get(obj1) == null)
				{
					collisions.put(obj1, new ArrayList<>());
				}

				if (collisions.get(obj2) == null)
				{
					collisions.put(obj2, new ArrayList<>());
				}

				if (collisions.get(obj1).contains(obj2) || collisions.get(obj2).contains(obj1))
				{
					continue;
				}

				collisions.get(obj1).add(obj2);
				collisions.get(obj2).add(obj1);

				if (!chkCollision(obj1, obj2))
				{
					continue;
				}

				if (removeObjects.contains(obj1) || removeObjects.contains(obj2))
				{
					continue;
				}

				collide(obj1, obj2);
			}
		}

		objects.removeAll(removeObjects);
		removeObjects.clear();
	}

	private void collide(GameObject obj1, GameObject obj2)
	{
		if (obj1 instanceof Spell && obj2 instanceof Spell)
		{
			collideSpells((Spell) obj1, (Spell) obj2);
		}

		if (obj1 instanceof Spell && obj2 instanceof DesktopPlayer)
		{
			collidePlayer((DesktopPlayer) obj2, (Spell) obj1);
		}

		if (obj2 instanceof Spell && obj1 instanceof DesktopPlayer)
		{
			collidePlayer((DesktopPlayer) obj1, (Spell) obj2);
		}
	}

	private void collidePlayer(DesktopPlayer player, Spell spell)
	{
		if (player == spell.getCaster())
		{
			return;
		}

		player.damage(spell.getDamage());

		removeObjects.add(spell);
	}

	private void collideSpells(Spell s1, Spell s2)
	{
		if (s1.getCaster() == s2.getCaster())
		{
			return;
		}

		if ((s1.isShield() && s2.isShield()) || (!s2.isShield() && !s1.isShield()))
		{
			return;
		}

		int s1Temp = s1.getDamage();
		s1.weaken(s2.getDamage());
		s2.weaken(s1Temp);

		System.out.println(
				"new damage after collision: " + s1 + "->" + s1.getDamage() + ", " + s2 + "->" + s2.getDamage());

		if (s1.getDamage() < 1)
		{
			removeObjects.add(s1);
		}

		if (s2.getDamage() < 1)
		{
			removeObjects.add(s2);
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

	private void refresh()
	{
		if (dummy.getLives() <= 0)
		{
			running = false;
		}
	}

}
