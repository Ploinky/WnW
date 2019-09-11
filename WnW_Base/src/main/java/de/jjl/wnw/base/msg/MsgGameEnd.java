package de.jjl.wnw.base.msg;

import de.jjl.wnw.base.util.WnWMap;

public class MsgGameEnd extends Msg
{
	public static final String TYPE = "GameEnd";

	private static final String PARAM_VICTOR = "Victor";

	private String victor;

	// TODO $Li 11.09.2019 Extract into interface, create general send method
	public void fromMap(WnWMap map)
	{
		victor = map.get(PARAM_VICTOR);
	}
	
	public MsgGameEnd(WnWMap map)
	{
		super(map);
	}

	public MsgGameEnd()
	{
	}

	public WnWMap getMsgMap()
	{
		WnWMap map = new WnWMap();

		map.put(MsgConst.TYPE, TYPE);
		map.put(PARAM_VICTOR, victor);

		return map;
	}

	public String getVictor()
	{
		return victor;
	}

	public void setVictor(String victor)
	{
		this.victor = victor;
	}
}
