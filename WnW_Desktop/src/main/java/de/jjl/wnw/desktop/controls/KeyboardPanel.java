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
				
				char[] s = e.getText().toCharArray();
				
				for(int i = 0; i < s.length; i++)
				{
					switch(s[i])
					{
						case '1':
							s[i] = '7';
							break;
						case '2':
							s[i] = '8';
							break;
						case '3':
							s[i] = '9';
							break;
						case '9':
							s[i] = '3';
							break;
						case '8':
							s[i] = '2';
							break;
						case '7':
							s[i] = '1';
							break;
					}
				}
				
				keyboard.addString(String.valueOf(s));
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
