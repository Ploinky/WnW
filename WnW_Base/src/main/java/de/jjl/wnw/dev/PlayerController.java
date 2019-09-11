/*
 * Copyright © 2019 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.dev;

import de.jjl.wnw.base.msg.Msg;

public interface PlayerController
{
	public String getInputString();
	
	public void updateGameState(String state);

	public boolean isConnected();

	public void sendMsg(Msg msg);
}
