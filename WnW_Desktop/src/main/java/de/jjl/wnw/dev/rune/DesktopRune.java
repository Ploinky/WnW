package de.jjl.wnw.dev.rune;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.jjl.wnw.desktop.gui.Drawable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DesktopRune extends BaseRune implements Drawable
{
	private Image img;

	private int x;

	private int y;

	public DesktopRune(String name, long rune, int damage, int x, int y)
	{
		this(name, rune, damage);

		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public DesktopRune(String name, long rune, int damage)
	{
		super(name, rune, damage);

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

	public Image getImage()
	{
		return img;
	}

	@Override
	public void drawOn(GraphicsContext graphics, long frameTime)
	{
		graphics.drawImage(img, x, y, 30, 30);
	}
}
