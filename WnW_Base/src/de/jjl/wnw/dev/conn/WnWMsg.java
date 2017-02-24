package de.jjl.wnw.dev.conn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WnWMsg
{
	// TODO MsgTyep and MsgParams-constants should be somewhere else
	public static final String MSGTYPE_MSGERROR = "MsgError";
	public static final String MSGPARAM_MSG = "Msg";
	
	public static final char MSG_PARAM_DELIMITER = ' ';
	public static final char PARAMS_DELIMITER = '~';
	public static final char KEY_VALUE_DELIMITER='=';
	
	public static WnWMsg parse(String msg) throws IllegalArgumentException
	{
		int idx = msg.indexOf(MSG_PARAM_DELIMITER);
		if(idx == -1 && msg.length() > 0)
		{
			return new WnWMsg(msg);
		}
		
		final String type = msg.substring(0, idx);
		
		Map<String, String> params = new HashMap<>();
		
		String paramString = msg.substring(idx + 1);
		for(String p : paramString.split("[" + PARAMS_DELIMITER + "]"))
		{
			int i = p.indexOf(KEY_VALUE_DELIMITER);
			
			if(i == -1)
			{
				throw new IllegalArgumentException(
						"Msg<" + msg
						+ " is invalid, parameter<" + p
						+ "> contains no Key-Value-Delimiter<" + KEY_VALUE_DELIMITER + ">");
			}
			if(idx == 0)
			{
				throw new IllegalArgumentException("Msg<" + msg + "> is invalid, parameter<" + p + "> has no key");
			}
			if(idx == p.length() - 1)
			{
				throw new IllegalArgumentException("Msg<" + msg + "> is invalid, parameter<" + p + "> has no value");
			}
			
			params.put(p.substring(0, i), p.substring(i + 1));
		}
		
		return new WnWMsg(type, params);
	}
	
	private final String type;
	private final Map<String, String> params;
	
	@SuppressWarnings("unchecked")
	public WnWMsg(String type)
	{
		this(type, Collections.EMPTY_MAP);
	}
	
	public WnWMsg(String type, Map<String, String> params)
	{
		this.type = type;
		this.params = new HashMap<>(params);
	}
	
	public String getType()
	{
		return type;
	}
	
	public Map<String, String> getParams()
	{
		return params;
	}
	
	public String buildMsgString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(type).append(MSG_PARAM_DELIMITER);
		
		boolean first = true;
		for(String key : params.keySet())
		{
			if(first)
			{
				first = false;
			}
			else
			{
				sb.append(PARAMS_DELIMITER);
			}
			sb.append(key).append(KEY_VALUE_DELIMITER).append(params.get(key));
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString()
	{
		return buildMsgString();
	}
}
