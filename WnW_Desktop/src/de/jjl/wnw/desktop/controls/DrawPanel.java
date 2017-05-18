package de.jjl.wnw.desktop.controls;


import java.io.*;

import javax.imageio.ImageIO;

import de.jjl.wnw.base.util.path.*;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
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
			WritableImage snapshot = snapshot(new SnapshotParameters(), null);
			
			try
			{
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("C:\\rune.png"));
			}
			catch(IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}
