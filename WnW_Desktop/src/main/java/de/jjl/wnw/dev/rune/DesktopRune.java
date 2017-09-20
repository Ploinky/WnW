package de.jjl.wnw.dev.rune;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class DesktopRune extends BaseRune
{
	private Image img;
	
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
}
