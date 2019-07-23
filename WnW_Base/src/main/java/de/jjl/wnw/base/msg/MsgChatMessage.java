package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgChatMessage
{
	public static final String TYPE = "ChatMessage";

	private static final String PARAM_PLAYER = "Player";

	private static final String PARAM_MESSAGE = "Message";

	private static final String PARAM_TIMESTAMP = "TimeStamp";

	private String msg;

	private String player;

	private String timeStamp;

	public String getPlayer()
	{
		return player;
	}

	public void setPlayer(String player)
	{
		this.player = player;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void fromMap(WnWMap map)
	{
		msg = map.get(PARAM_MESSAGE);
		player = map.get(PARAM_PLAYER);
		timeStamp = map.get(PARAM_TIMESTAMP);
	}

	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();

		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_MESSAGE, msg);
		map.put(PARAM_PLAYER, player);
		map.put(PARAM_TIMESTAMP, timeStamp);

		return map;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
}
