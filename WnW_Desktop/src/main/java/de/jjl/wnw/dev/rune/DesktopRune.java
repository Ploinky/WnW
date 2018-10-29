package de.jjl.wnw.dev.rune;

import java.io.*;

import javax.imageio.ImageIO;

import de.jjl.wnw.desktop.game.DesktopPlayer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DesktopRune extends BaseRune
{
	private DesktopPlayer caster;

	private Image img;

	private int x;

	private int y;

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

	public DesktopRune(String name, long rune, int damage, int x, int y)
	{
		this(name, rune, damage);

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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	@Override
	public void move(float frameTime)
	{
		// Runes cannot move... yet
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
