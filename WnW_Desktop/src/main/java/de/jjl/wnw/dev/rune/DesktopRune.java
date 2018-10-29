package de.jjl.wnw.dev.rune;

import java.io.*;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DesktopRune extends BaseRune
{
	private Image img;

	private int x;

	private int y;

	public DesktopRune(String name, long rune)
	{
		super(name, rune);

		// TODO $Li 20.09.2017
		try
		{
			img = SwingFXUtils.toFXImage(ImageIO.read(new File("res/" + rune + ".png")), null);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DesktopRune(String name, long rune, int x, int y)
	{
		this(name, rune);

		this.x = x;
		this.y = y;
	}

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		graphics.drawImage(img, x, y, 30, 30);
	}

	public Image getImage()
	{
		return img;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public void move(float frameTime)
	{
		throw new UnsupportedOperationException("Method 'move' not implemented for DesktopRunes yet");
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getWidth()
	{
		return (int) img.getWidth();
	}

	@Override
	public int getHeight()
	{
		return (int) img.getHeight();
	}
}
