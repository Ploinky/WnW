/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.controls;

import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawPanel extends Pane
{
	private WnWDesktopPath path;

	private BooleanProperty editable;
	private ReadOnlyBooleanWrapper drawing;
	private ObjectProperty<EventHandler<DrawPathEvent>> onStartDrawPath;
	private ObjectProperty<EventHandler<DrawPathEvent>> onDrawPath;
	private ObjectProperty<EventHandler<DrawPathEvent>> onPathDrawn;

	public DrawPanel()
	{
		onStartDrawPath = new SimpleObjectProperty<EventHandler<DrawPathEvent>>(this, "onStartDrawPath", null)
		{
			@Override
			protected void invalidated()
			{
				addEventHandler(DrawPathEvent.START_DRAW_PATH, get());
			}
		};
		onDrawPath = new SimpleObjectProperty<EventHandler<DrawPathEvent>>(this, "onDrawPath", null)
		{
			@Override
			protected void invalidated()
			{
				addEventHandler(DrawPathEvent.DRAW_PATH, get());
			}
		};
		onPathDrawn = new SimpleObjectProperty<EventHandler<DrawPathEvent>>(this, "onPathDrawn", null)
		{
			@Override
			protected void invalidated()
			{
				addEventHandler(DrawPathEvent.PATH_DRAWN, get());
			}
		};

		editable = new SimpleBooleanProperty(this, "editable", true);
		drawing = new ReadOnlyBooleanWrapper(this, "drawing", false);

		addEventHandler(MouseEvent.MOUSE_PRESSED, e -> startDraw(e.getX(), e.getY()));
		addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> draw(e.getX(), e.getY()));
		addEventHandler(MouseEvent.MOUSE_RELEASED, e -> endDraw());
	}

	protected void startDraw(double x, double y)
	{
		if (isDrawing())
		{
			endDraw();
		}
		drawing.set(true);
		getChildren().clear();
		
		path = new WnWDesktopPath(new WnWDisplaySystem((int) getWidth(), (int) getHeight(), true, false, 0, 0));
		getChildren().add(path.getFXPath());

		Event.fireEvent(this, new DrawPathEvent(this, this, DrawPathEvent.START_DRAW_PATH, path));

		draw(x, y);
	}

	protected void draw(double x, double y)
	{
		if (!isDrawing())
		{
			return;
		}

		// If we're no longer drawing on the Panel, we're not adding any more points to our path. 
		// We do, however, want to continue the currently drawn line all the way to the edge of the panel...
		y = y < 0 ? 0 : (y > getHeight() ? getHeight() : y);
		x = x < 0 ? 0 : (x > getWidth() ? getWidth() : x);

		path.addPoint((int) x, (int) y);
		Event.fireEvent(this, new DrawPathEvent(this, this, DrawPathEvent.DRAW_PATH, path));
	}

	public void endDraw()
	{
		if (!isDrawing())
		{
			return;
		}

		drawing.set(false);

		Event.fireEvent(this, new DrawPathEvent(this, this, DrawPathEvent.PATH_DRAWN, path));
	}

	public final BooleanProperty editableProperty()
	{
		return editable;
	}

	public final boolean isEditable()
	{
		return editable.get();
	}

	public final void setEditable(boolean editable)
	{
		this.editable.set(editable);
	}

	public final ReadOnlyBooleanProperty drawingProperty()
	{
		return drawing.getReadOnlyProperty();
	}

	public final boolean isDrawing()
	{
		return drawing.get();
	}

	public final ObjectProperty<EventHandler<DrawPathEvent>> onStartDrawPathProperty()
	{
		return onStartDrawPath;
	}

	public final EventHandler<DrawPathEvent> getOnStartDrawPath()
	{
		return onStartDrawPath.get();
	}

	public final void setOnStartDrawPath(EventHandler<DrawPathEvent> value)
	{
		onStartDrawPath.set(value);
	}

	public final ObjectProperty<EventHandler<DrawPathEvent>> onDrawPathProperty()
	{
		return onDrawPath;
	}

	public final EventHandler<DrawPathEvent> getOnDrawPath()
	{
		return onDrawPath.get();
	}

	public final void setOnSDrawPath(EventHandler<DrawPathEvent> value)
	{
		onDrawPath.set(value);
	}

	public final ObjectProperty<EventHandler<DrawPathEvent>> onPathDrawnProperty()
	{
		return onPathDrawn;
	}

	public final EventHandler<DrawPathEvent> getOnPathDrawn()
	{
		return onPathDrawn.get();
	}

	public final void setOnPathDrawn(EventHandler<DrawPathEvent> value)
	{
		onPathDrawn.set(value);
	}
}
