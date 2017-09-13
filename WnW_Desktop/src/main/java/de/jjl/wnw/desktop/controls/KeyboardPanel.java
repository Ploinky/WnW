package de.jjl.wnw.desktop.controls;

import de.jjl.wnw.base.input.WnWKeyboardInput;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class KeyboardPanel extends Pane
{
	WnWKeyboardInput keyboard = new WnWKeyboardInput("");
	
	private ObjectProperty<EventHandler<ActionEvent>> onStringEntered;
	
	public KeyboardPanel()
	{
		onStringEntered = new SimpleObjectProperty<>();
		
		setFocusTraversable(true);
		
		sceneProperty().addListener((p, o, n) ->
		{
			if(n == null)
			{
				return;
			}
			
			
			n.setOnKeyPressed(e ->
			{
				if(e.getCode() == KeyCode.ENTER)
				{
					onStringEntered.get().handle(new ActionEvent());
					keyboard.reset();
					return;
				}
				
				keyboard.addString(e.getCode().toString());
			});
		});
	}
	
	public void setOnStringEntered(EventHandler<ActionEvent> value)
	{
		onStringEntered.set(value);
	}
	
	public WnWKeyboardInput getInput()
	{
		return keyboard;
	}
}
