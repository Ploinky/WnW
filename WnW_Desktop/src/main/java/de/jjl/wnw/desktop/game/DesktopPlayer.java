package de.jjl.wnw.desktop.game;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.jjl.wnw.dev.game.Player;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class DesktopPlayer implements Player
{
	private boolean faceLeft;

	private int height = 100;

	private Image image;

	private int lives = 5;

	private int width = 61;

	private int x;

	private int y;

	public DesktopPlayer(double x, double y)
	{
		try
		{
			image = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/Lil' Wizzy Cropped.png")),
					null);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		faceLeft = false;
	}

	public void damage(int damage)
	{
		lives -= damage;
	}

	/**
	 * This is just a hack until I think of something better...
	 */
	@Override
	public void faceLeft()
	{
		faceLeft = true;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public int getLives()
	{
		return lives;
	}

	@Override
	public int getWidth()
	{
		return width;
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
	public boolean isFaceLeft()
	{
		return faceLeft;
	}

	@Override
	public void update(float frameTime)
	{
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}
}
