package de.jjl.wnw.desktop.game;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.jjl.wnw.desktop.gui.Drawable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DesktopPlayer implements Drawable
{
	private Image image;
	
	private int x;
	
	private int y;
	
	private int width = 61;
	
	private int height = 100;
	
	public DesktopPlayer(double x, double y)
	{
		try
		{
			image = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/Lil' Wizzy Cropped.png")), null);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void drawOn(GraphicsContext graphics, long frameTime)
	{
		graphics.drawImage(image, x, y, 61, 100);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
}
