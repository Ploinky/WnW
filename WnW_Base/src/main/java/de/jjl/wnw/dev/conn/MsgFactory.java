package de.jjl.wnw.dev.conn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MsgFactory
{
	private MsgFactory()
	{

	}

	public static WnWMsg playerJoined(String playerName)
	{
		Map<String, String> params = new HashMap<>();
		params.put("PLAYER_CHANGE_TYPE", "JOINED");
		params.put("PLAYER_NAME", playerName);
		return new WnWMsg(MsgType.PLAYER_CHANGE, params);
	}

	public static WnWMsg msg(String clName, String msgContent)
	{
		Map<String, String> params = new HashMap<>();
		params.put("CLIENT", clName);
		params.put("VALUE", msgContent);
		return new WnWMsg(MsgType.MSG, params);
	}

	public static WnWMsg playerList(String... players)
	{
		Map<String, String> params = new HashMap<>();

		for(String p : players)
		{
			params.put("PLAYER" + params.size(), p);
		}

		return new WnWMsg(MsgType.PLAYER_LIST, params);
	}
}
