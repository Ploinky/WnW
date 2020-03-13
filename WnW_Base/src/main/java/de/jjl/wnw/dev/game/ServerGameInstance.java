package de.jjl.wnw.dev.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.base.msg.MsgConst;
import de.jjl.wnw.base.msg.MsgGameEnd;
import de.jjl.wnw.base.msg.MsgGameState;
import de.jjl.wnw.base.msg.MsgPlayerInput;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.util.WnWMap;
import de.jjl.wnw.dev.PlayerController;
import de.jjl.wnw.dev.log.Debug;
import de.jjl.wnw.dev.net.NetPlayerController;
import de.jjl.wnw.dev.rune.RuneUtil;
import de.jjl.wnw.dev.spell.Spell;
import de.jjl.wnw.dev.spell.SpellUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class ServerGameInstance extends GameInstance
{
	public static void main(String[] args)
	{
		new ServerGameInstance();
	}
	
	private ServerSocket serverSocket;
	
	private boolean isRunning;
	
	private ObservableList<MsgChatMessage> chatMsgs;
	
	private void startConnectionThread()
	{
		isRunning = true;
		
		new Thread(() ->
		{
			while(isRunning)
			{
				try
				{
					Debug.log("Waiting for connections...");

					Socket s = serverSocket.accept();

					Debug.log("Accepted connection from <" + s.getInetAddress() + ">");
					
					NetPlayerController controller = new NetPlayerController(s);
					
					if(ServerGameInstance.getInstance().getPlayer1Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 1.");
						ServerGameInstance.getInstance().setControllerPlayer1(controller);
					}
					else if(ServerGameInstance.getInstance().getPlayer2Controller() == null)
					{
						Debug.log("Client at <" + s.getInetAddress() + "> is controller for player 2.");
						ServerGameInstance.getInstance().setControllerPlayer2(controller);
					}
				}
				catch (SocketTimeoutException e)
				{
					// Shit happens...
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void startServerThread()
	{
		isRunning = true;
		
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				if(!isRunning)
				{
					cancel();
				}
				
				ServerGameInstance.getInstance().handleFrame(System.currentTimeMillis());
			}
		};
		
		Timer t = new Timer();
		t.schedule(task, 0, 1000 / 60);
	}

	private static ServerGameInstance instance;

	public static ServerGameInstance getInstance()
	{
		if (instance == null)
		{
			instance = new ServerGameInstance();
		}

		return instance;
	}

	private List<WnWRune> currentRunes;

	private long frameTime = 0;

	private long lastFrame = System.currentTimeMillis();

	// TODO $Li 25.02.2019 this does not seem like a good idea tbh...
	private long maxShieldLength = 500;

	private Collection<GameObject> objects;

	private List<Long> p1Combo;

	private List<Long> p2Combo;

	private PlayerController player1Controller;

	private PlayerController player2Controller;

	private Collection<GameObject> removeObjects;

	private boolean running;

	private ServerGameInstance()
	{
		objects = new CopyOnWriteArrayList<>();
		removeObjects = new CopyOnWriteArrayList<>();
		currentRunes = new CopyOnWriteArrayList<>();
		chatMsgs = FXCollections.observableArrayList();
		p1Combo = new ArrayList<>();
		p2Combo = new ArrayList<>();

		// TODO $Li 19.09.2019 dynamic arena sizing (and player positioning)
		player1 = new GamePlayer(Const.PLAYER1_POS_X, Const.PLAYER1_POS_Y);
		player2 = new GamePlayer(Const.PLAYER2_POS_X, Const.PLAYER2_POS_Y);
		player2.faceLeft();

		running = true;

		objects.add(player1);
		objects.add(player2);
		
		try
		{
			int port = 51234;
			Debug.log("Creating server at port <" + port + ">");
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(0);
			Debug.log("Server created at port <" + serverSocket.getLocalPort() + ">");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startConnectionThread();
		
		startServerThread();
	}

	public void drawDebug(GraphicsContext graphics)
	{
		graphics.setFont(new Font(10));
		graphics.fillText("GameObjects: " + objects.size(), 20, 60);
	}

	public ObservableList<MsgChatMessage> getChatMsgs()
	{
		return chatMsgs;
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
	
	public void sendServerChatMessage(MsgChatMessage msg)
	{
		sendChatMessage(msg);
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
		WnWRune dRune = RuneUtil.getRune(player1, rune);

		if (dRune != null)
		{
			dRune.setX(10 + currentRunes.size() * 40);
			dRune.setY(10);
			currentRunes.add(dRune);
		}
	}

	private void addRunePlayer2(Long rune)
	{
		WnWRune dRune = RuneUtil.getRune(player2, rune);

		if (dRune != null)
		{
			dRune.setX(10 + currentRunes.size() * 40);
			dRune.setY(10);
			currentRunes.add(dRune);
		}
	}

	private void cast(Player player, List<Long> combo, boolean shield)
	{
		Spell spell = SpellUtil.getSpell(player, combo, shield);

		if (spell != null)
		{
			spell.setPos(player.getX(), player.getY());
			objects.add(spell);
			System.out.println("Adding spell <" + spell + "> from <" + player + ">");
		}

		currentRunes.forEach(dRune -> objects.remove(dRune));
		objects.removeAll(currentRunes);
		currentRunes.clear();
	}

	private void checkPlayer1Input()
	{
		if (player1Controller == null || !player1Controller.isConnected())
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
			handleMessage(msgMap, player1);
		}
	}

	private void checkPlayer2Input()
	{
		if (player2Controller == null || !player2Controller.isConnected())
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
			handleMessage(msgMap, player2);
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
				cast(player2, p2Combo, false);
				p1Combo.clear();
			}
			else if (s.equals("S"))
			{
				cast(player2, p2Combo, true);
				p2Combo.clear();
			}
			else
			{
				if (!s.matches("[0-9]+"))
				{
					System.err.println("UNKNOWN INPUT PLAYER 1: <" + s + ">");
					continue;
				}

				addRunePlayer2(Long.valueOf(s));

				p2Combo.add(Long.valueOf(s));
			}
		}
	}

	private void checkPlayerInput()
	{
		checkPlayer1Input();
		checkPlayer2Input();
	}

	private boolean chkCollision(GameObject obj1, GameObject obj2)
	{
		if ((Spell.class.isInstance(obj1) && ((Spell) obj1).getCaster() == obj2)
				|| (Spell.class.isInstance(obj2) && ((Spell) obj2).getCaster() == obj1))
		{
			return false;
		}

		if (obj1.getX() + obj1.getWidth() / 2 < obj2.getX()
				|| obj2.getX() + obj2.getWidth() / 2 < obj1.getX()
				|| obj1.getY() + obj1.getHeight() < obj2.getY()
				|| obj2.getY() + obj2.getHeight() < obj1.getY())
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
				if (obj1 == obj2 || (GamePlayer.class.isInstance(obj1) && GamePlayer.class.isInstance(obj2))
						|| WnWRune.class.isInstance(obj1) || WnWRune.class.isInstance(obj2))
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
			System.out.println("SPELL DEFENDED!");
			removeObjects.add(s1);
		}

		if (s2.getDamage() < 1)
		{
			System.out.println("SPELL DEFENDED!");
			removeObjects.add(s2);
		}
	}

	private void handleMessage(WnWMap msgMap, Player player)
	{
		switch (msgMap.get(MsgConst.TYPE))
		{
			case MsgChatMessage.TYPE:
				MsgChatMessage msgChat = new MsgChatMessage();
				msgChat.fromMap(msgMap);
				sendChatMessage(msgChat);
				break;
			case MsgPlayerInput.TYPE:
				MsgPlayerInput msgInput = new MsgPlayerInput();
				msgInput.fromMap(msgMap);
				handlePlayerInput(msgInput, player);
				break;
		}
	}

	private void handlePlayerInput(MsgPlayerInput msg, Player player)
	{
		String[] input = msg.getInput().split("\\|");

		List<Long> combo = player == player1 ? p1Combo : p2Combo;

		for (String s : input)
		{
			if (s == null || s.isEmpty())
			{
				continue;
			}

			if (s.equals("A"))
			{
				System.out.println("Player casting <" + combo + ">");
				cast(player, combo, false);
				combo.clear();
			}
			else if (s.equals("S"))
			{
				System.out.println("Player casting shield <" + combo + ">");
				cast(player, combo, true);
				combo.clear();
			}
			else
			{
				if (!s.matches("[0-9]+"))
				{
					System.err.println("UNKNOWN INPUT PLAYER 1: <" + s + ">");
					continue;
				}

				if (player == player1)
				{
					addRunePlayer1(Long.valueOf(s));
				}
				else
				{
					addRunePlayer2(Long.valueOf(s));
				}

				combo.add(Long.valueOf(s));
			}
		}
	}

	private void refresh()
	{
		if (player1.getLives() <= 0 || player2.getLives() <= 0)
		{
			if(player1.getLives() <= 0)
			{
				sendGameEndMessage("Player1");
			}
			else if(player2.getLives() <= 0)
			{
				sendGameEndMessage("Player2");
			}
			
			running = false;
		}

		objects.stream().filter(Spell.class::isInstance).map(Spell.class::cast).forEach(spell ->
		{
			if (spell.isShield() && System.currentTimeMillis() - spell.getCastTime() > maxShieldLength)
			{
				removeObjects.add(spell);
			}

			if (spell.getX() > 10000)
			{
				removeObjects.add(spell);
			}
		});
	}
	
	private void sendGameEndMessage(String victor)
	{
		MsgGameEnd msg = new MsgGameEnd();
		msg.setVictor(victor);
		
		if (player1Controller != null && player1Controller.isConnected())
		{
			try
			{
				player1Controller.sendMsg(msg);
			}
			catch (RuntimeException e)
			{
				System.out.println(e);
				player1Controller = null;
			}
		}

		if (player2Controller != null && player2Controller.isConnected())
		{
			try
			{
				player2Controller.sendMsg(msg);
			}
			catch (RuntimeException e)
			{
				player2Controller = null;
			}
		}
	}
	
	private void sendChatMessage(MsgChatMessage msg)
	{
		chatMsgs.add(msg);
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
		msg.setP1Character(player1);
		msg.setP2Character(player2);
		msg.setP1Lives(player1.getLives());
		msg.setP2Lives(player2.getLives());

		objects.stream().filter(Spell.class::isInstance).map(Spell.class::cast)
				.forEach(spell -> msg.addSpell(spellToMap(spell)));

		WnWMap msgMap = null;
		try
		{
			msgMap = msg.getMsgMap();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (player1Controller != null && player1Controller.isConnected())
		{
			try
			{
				player1Controller.updateGameState(msgMap.toString());
			}
			catch (RuntimeException e)
			{
				System.out.println(e);
				player1Controller = null;
			}
		}

		if (player2Controller != null && player2Controller.isConnected())
		{
			try
			{
				player2Controller.updateGameState(msgMap.toString() + "\n");
			}
			catch (RuntimeException e)
			{
				player2Controller = null;
			}
		}
	}

	private void updateObjects(long frameTime)
	{
		for (GameObject obj : objects)
		{
			if (!(obj instanceof Player))
			{
				obj.update(frameTime);
			}
		}
	}

}
