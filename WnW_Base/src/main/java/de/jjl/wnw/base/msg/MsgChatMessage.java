package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgChatMessage extends Msg
{
	public static final String TYPE = "ChatMessage";

	private static final String PARAM_MESSAGE = "Message";

	private static final String PARAM_PLAYER = "Player";

	private static final String PARAM_TIMESTAMP = "TimeStamp";

	private String msg;

	private String player;

	private String timeStamp;
	
	@Override
	public void fromMap(WnWMap map)
	{
		msg = map.get(PARAM_MESSAGE);
		player = map.get(PARAM_PLAYER);
		timeStamp = map.get(PARAM_TIMESTAMP);
	}

	public String getMsg()
	{
		return msg;
	}

	@Override
	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();

		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_MESSAGE, msg);
		map.put(PARAM_PLAYER, player);
		map.put(PARAM_TIMESTAMP, timeStamp);

		return map;
	}

	public String getPlayer()
	{
		return player;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void setPlayer(String player)
	{
		this.player = player;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	public String toChatString()
	{
		// TODO Auto-generated method stub
		return timeStamp + " " + player + ": " + msg;
	}
}
