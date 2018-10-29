package de.jjl.wnw.desktop.game;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.jjl.wnw.dev.game.GameObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class DesktopPlayer extends GameObject
{
	private Image image;

	private int x;

	private int y;

	private int width = 61;

	private int height = 100;

	private boolean faceLeft;

	private int lives = 5;

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

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		graphics.drawImage(image, x + (faceLeft ? width : 0), y, faceLeft ? -width : width, height);
		graphics.setFont(new Font(20));
		graphics.fillText("" + lives, x + 20, y - 20);
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

	/**
	 * This is just a hack until I think of something better...
	 */
	public void faceLeft()
	{
		faceLeft = true;
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
		throw new UnsupportedOperationException("Method 'move' not implemented for DesktopPlayer yet");
	}

	public void damage(int damage)
	{
		lives -= damage;
	}

	public int getLives()
	{
		return lives;
	}
}
