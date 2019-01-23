package de.jjl.wnw.desktop.controls;

import javafx.event.*;
import javafx.scene.input.InputEvent;

public class KeyboardInputEvent extends InputEvent
{
	private static final long serialVersionUID = 4119709010537181465L;

	public static final EventType<KeyboardInputEvent> INPUT_FINISHED = new EventType<>(InputEvent.ANY,
			"INPUT_FINISHED");

	private final String input;

	public KeyboardInputEvent(EventType<? extends InputEvent> eventType, String input)
	{
		super(eventType);

		this.input = input;
	}

	public KeyboardInputEvent(Object source, EventTarget target, EventType<? extends KeyboardInputEvent> eventType,
			String input)
	{
		super(source, target, eventType);

		this.input = input;
	}

	public String getInput()
	{
		return input;
	}

}
