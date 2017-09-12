package de.jjl.wnw.desktop.controls;

import de.jjl.wnw.base.util.path.WnWPath;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class DrawPathEvent extends InputEvent
{
	public static final EventType<DrawPathEvent> ANY = new EventType<>(InputEvent.ANY, "PATH");
	public static final EventType<DrawPathEvent> START_DRAW_PATH = new EventType<>(ANY, "StartDrawPath");
	public static final EventType<DrawPathEvent> DRAW_PATH = new EventType<>(ANY, "DrawPath");
	public static final EventType<DrawPathEvent> PATH_DRAWN = new EventType<>(ANY, "PahDrawn");
	
	private final WnWPath path;
	
	public DrawPathEvent(EventType<? extends DrawPathEvent> eventType, WnWPath path)
	{
		super(eventType);
		
		this.path = path;
	}
	
	public DrawPathEvent(Object source, EventTarget target, EventType<? extends DrawPathEvent> eventType, WnWPath path)
	{
		super(source, target, eventType);
		
		this.path = path;
	}
	
	public WnWPath getPath()
	{
		return path;
	}
}
