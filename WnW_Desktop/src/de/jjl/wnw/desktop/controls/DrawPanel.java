package de.jjl.wnw.desktop.controls;


import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.scene.layout.Pane;

public class DrawPanel extends Pane
{
	private WnWDesktopPath path;
	
	private WnWDisplaySystem disSys;

	public DrawPanel()
	{
		disSys = new WnWDisplaySystem(new WnWPoint(0, 0),
			new WnWPoint((int) getWidth(), (int)getHeight()),
			true, false);

		

		setOnMousePressed(e ->
		{
			if(path != null)
			{
				getChildren().remove(path.getFXPath());
			}
			path = new WnWDesktopPath(disSys);
			getChildren().add(path.getFXPath());

			path.addPoint((float) e.getX(), (float) e.getY());
		});
		
		setOnMouseDragged(e ->
		{
			path.addPoint((float) e.getX(), (float) e.getY());
		});
		
		setOnMouseReleased(e ->
		{
			// TODO $Li 18.05.2017 display rune in chain
		});
	}
}
