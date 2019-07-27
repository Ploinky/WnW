package de.jjl.wnw.dev.game;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.PlayerController;
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

	private List<BaseRune> currentRunes;

	private long frameTime = 0;

	private long lastFrame = System.currentTimeMillis();

	// TODO $Li 25.02.2019 this does not seem like a good idea tbh...
	private long maxShieldLength = 500;

	private Collection<GameObject> objects;

	private List<Long> p1Combo;

	private Player player1;

	private PlayerController player1Controller;

	private Player player2;

	private PlayerController player2Controller;

	private Collection<GameObject> removeObjects;

	private boolean running;

	private GameInstance()
	{
		objects = new CopyOnWriteArrayList<>();
		removeObjects = new CopyOnWriteArrayList<>();
		currentRunes = new CopyOnWriteArrayList<>();
		p1Combo = new ArrayList<>();

		player1 = new GamePlayer(0, 0);
		player2 = new GamePlayer(0, 0);
		player2.faceLeft();

		running = true;

		objects.add(player1);
		objects.add(player2);
	}

	public void drawDebug(GraphicsContext graphics)
	{
		graphics.setFont(new Font(10));
		graphics.fillText("GameObjects: " + objects.size(), 20, 60);
	}

	public Collection<GameObject> getObjects()
	{
		return objects;
	}

	public PlayerController getPlayer1Controller()
	{
		return player1Controller;
	}

	public PlayerController getPlayer2Controller()
	{
		return player2Controller;
	}

	public void handleFrame(long now)
	{
		frameTime = now - lastFrame;
		lastFrame = now;

		updateObjects(frameTime);
		collide();
		checkPlayerInput();
		refresh();
		sendGameState();
	}

	public boolean isRunning()
	{
		return running;
	}

	public void setControllerPlayer1(PlayerController controller)
	{
		player1Controller = controller;
	}

	public void setControllerPlayer2(PlayerController controller)
	{
		player2Controller = controller;
	}

	public void setDrawSize(double width, double height)
	{
		player1.setX((int) (width / 100 * 10));
		player1.setY((int) (height / 100 * 70));

		player2.setX((int) (width / 100 * 90));
		player2.setY((int) (height / 100 * 70));
	}

	private void addRunePlayer1(Long rune)
	{
		BaseRune dRune = RuneUtil.getRune(player1, rune);

		if (dRune != null)
		{
			dRune.setX(10 + currentRunes.size() * 40);
			dRune.setY(10);
			currentRunes.add(dRune);
			objects.add(dRune);
		}
	}

	private void cast(Player player, List<Long> combo, boolean shield)
	{
		Spell spell = SpellUtil.getSpell(player, combo, shield);

		System.out.println(combo);

		if (spell != null)
		{
			spell.setPos(player.getX(), player.getY());
			objects.add(spell);
			objects.add(spell);
		}

		currentRunes.forEach(dRune -> objects.remove(dRune));
		objects.removeAll(currentRunes);
		currentRunes.clear();
	}

	private void checkPlayerInput()
	{
		checkPlayer1Input();
		checkPlayer2Input();
	}
	private void checkPlayer2Input()
	{
		if (player2Controller == null)
		{
			return;
		}

		String inputString = player2Controller.getInputString();

		if (inputString == null || inputString.isEmpty())
		{
			return;
		}

		WnWMap msgMap = new WnWMap();
		msgMap.fromString(inputString);

		if (msgMap.containsKey(MsgConst.TYPE))
		{
			handleMessage(msgMap);
			return;
		}

		String[] p2Input = inputString.split("\\|");

		for (String s : p2Input)
		{
			if (s == null || s.isEmpty())
			{
				continue;
			}

			if (s.equals("A"))
			{
				cast(player2, p1Combo, false);
				p1Combo.clear();
			}
			else if (s.equals("S"))
			{
				cast(player2, p1Combo, true);
				p1Combo.clear();
			}
			else
			{
				if (!s.matches("[0-9]+"))
				{
					System.err.println("UNKNOWN INPUT PLAYER 1: <" + s + ">");
					continue;
				}

				addRunePlayer1(Long.valueOf(s));

				p1Combo.add(Long.valueOf(s));
			}
		}
	}
	
	private void checkPlayer1Input()
	{
		if (player1Controller == null)
		{
			return;
		}

		String inputString = player1Controller.getInputString();

		if (inputString == null || inputString.isEmpty())
		{
			return;
		}

		WnWMap msgMap = new WnWMap();
		msgMap.fromString(inputString);

		if (msgMap.containsKey(MsgConst.TYPE))
		{
			handleMessage(msgMap);
			return;
		}
		
		String[] p1Input = inputString.split("\\|");

		for (String s : p1Input)
		{
			if (s == null || s.isEmpty())
			{
				continue;
			}

			if (s.equals("A"))
			{
				cast(player1, p1Combo, false);
				p1Combo.clear();
			}
			else if (s.equals("S"))
			{
				cast(player1, p1Combo, true);
				p1Combo.clear();
			}
			else
			{
				if (!s.matches("[0-9]+"))
				{
					System.err.println("UNKNOWN INPUT PLAYER 1: <" + s + ">");
					continue;
				}

				addRunePlayer1(Long.valueOf(s));

				p1Combo.add(Long.valueOf(s));
			}
		}
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

		if (obj1 instanceof Spell && obj2 instanceof GamePlayer)
		{
			collidePlayer((GamePlayer) obj2, (Spell) obj1);
		}

		if (obj2 instanceof Spell && obj1 instanceof GamePlayer)
		{
			collidePlayer((GamePlayer) obj1, (Spell) obj2);
		}
	}

	private void collidePlayer(GamePlayer player, Spell spell)
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

		if (s1.getDamage() < 1)
		{
			removeObjects.add(s1);
		}

		if (s2.getDamage() < 1)
		{
			removeObjects.add(s2);
		}
	}

	private void handleMessage(WnWMap msgMap)
	{
		switch (msgMap.get(MsgConst.TYPE))
		{
		case MsgChatMessage.TYPE:
			MsgChatMessage msg = new MsgChatMessage();
			msg.fromMap(msgMap);
			sendChatMessage(msg);
			System.out.println("Received message <" + msg + ">");
			break;
		}
	}

	private void updateObjects(long frameTime)
	{
		for (GameObject obj : objects)
		{
			if (!(obj instanceof Player) && !(obj instanceof DesktopRune))
			{
				obj.update(frameTime);
			}
		}
	}

	private void refresh()
	{
		if (player1.getLives() <= 0 || player2.getLives() <= 0)
		{
			running = false;
		}

		objects.stream().filter(o -> o instanceof Spell).forEach(o ->
		{
			Spell s = (Spell) o;

			if (System.currentTimeMillis() - s.getCastTime() > maxShieldLength)
			{
				removeObjects.add(s);
			}
		});
	}

	private void sendChatMessage(MsgChatMessage msg)
	{
		if (player1Controller != null && player1Controller.isConnected())
		{
			player1Controller.sendMsg(msg);
		}

		if (player2Controller != null && player2Controller.isConnected())
		{
			player2Controller.sendMsg(msg);
		}
	}

	private void sendGameState()
	{
		MsgGameState msg = new MsgGameState();
		msg.setGameTime(System.currentTimeMillis());
		msg.setP1Character("");
		msg.setP2Character("");

		WnWMap msgMap = msg.getMsgMap();

		if (player1Controller != null && player1Controller.isConnected())
		{
			try
			{
				player1Controller.updateGameState(msgMap.toString() + "\n");
			}
			catch(RuntimeException e)
			{
				player1Controller = null;
			}
		}

		if (player2Controller != null && player2Controller.isConnected())
		{
			try
			{
				player2Controller.updateGameState(msgMap.toString() + "\n");
			}
			catch(RuntimeException e)
			{
				player2Controller = null;
			}
		}
	}

}
