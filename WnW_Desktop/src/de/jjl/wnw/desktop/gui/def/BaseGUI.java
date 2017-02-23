/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.def;

import java.util.*;
import java.util.function.Consumer;

import de.jjl.wnw.desktop.gui.*;

public abstract class BaseGUI implements GUI
{
	protected List<GuiListener> listeners;
	
	public BaseGUI()
	{
		listeners = new ArrayList<>();
	}
	
	public void addListener(GuiListener g)
	{
		listeners.add(g);
	}
	
	public void removeListener(GuiListener g)
	{
		listeners.remove(g);
	}
	
	protected void fireEvent(Consumer<GuiListener> c)
	{
		listeners.forEach(c::accept);
	}
}
