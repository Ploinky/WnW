/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui;

import de.jjl.wnw.base.player.Player;

public interface ServerGui
{
	public void addClient(Player p);
	
	public void removeClient(Player p);
	
	public void setVisible(boolean visible);
}
