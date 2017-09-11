/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class DrawPanel extends Pane
{
	private Path path;
	
	private BooleanProperty editable;
	
	public DrawPanel()
	{
		addEventHandler(MouseEvent.MOUSE_PRESSED, e ->
		{
			getChildren().clear();
			path = new Path(new MoveTo(e.getX(), e.getY()));
			getChildren().add(path);
		});
		
		addEventHandler(MouseEvent.MOUSE_DRAGGED, e ->
		{
			path.getElements().add(new LineTo(e.getX(), e.getY()));
		});
	}
	
	public void setEditable(boolean editable)
	{
		this.editable.set(editable);
	}
	
	public BooleanProperty editableProperty()
	{
		return editable;
	}
	
	public boolean isEditable()
	{
		return editable.get();
	}
}
