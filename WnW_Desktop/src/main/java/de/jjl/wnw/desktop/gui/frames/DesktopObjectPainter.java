package de.jjl.wnw.desktop.gui.frames;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.jjl.wnw.dev.game.Drawable;
import de.jjl.wnw.dev.game.GameObject;
import de.jjl.wnw.dev.game.GamePlayer;
import de.jjl.wnw.dev.rune.BaseRune;
import de.jjl.wnw.dev.spell.Spell;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DesktopObjectPainter
{
	private GraphicsContext graphics;

	private Image playerSprite;

	private Map<Long, Image> runeSprites;
	private Map<String, Image> spellSprites;

	public DesktopObjectPainter(GraphicsContext graphics)
	{
		this.graphics = graphics;

		try
		{
			loadResources();
		}
		catch (IOException e)
		{
			// TODO $Li 23.02.2019 How do we clean this up?!
			e.printStackTrace();
		}
	}

	private void loadResources() throws IOException
	{
		playerSprite = SwingFXUtils
				.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/Lil' Wizzy Cropped.png")), null);

		runeSprites = new HashMap<>();
		// TODO $Li 23.02.2019 boy we REALLY need to fix this!
		runeSprites.put(14789l,
				SwingFXUtils.toFXImage(ImageIO.read(new File("res/14789.png")), new WritableImage(40, 40)));
		runeSprites.put(159l, SwingFXUtils.toFXImage(ImageIO.read(new File("res/159.png")), new WritableImage(40, 40)));
		runeSprites.put(753l, SwingFXUtils.toFXImage(ImageIO.read(new File("res/753.png")), new WritableImage(40, 40)));

		spellSprites = new HashMap<>();
		BufferedImage image = new BufferedImage(30, 30, BufferedImage.TYPE_INT_BGR);
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(java.awt.Color.red);
		graphics.fillRect(0, 0, 30, 30);

		spellSprites.put("159", SwingFXUtils.toFXImage(image, null));

		graphics.setColor(java.awt.Color.green);
		graphics.fillRect(0, 0, 30, 30);
		spellSprites.put("753", SwingFXUtils.toFXImage(image, null));

		graphics.setColor(java.awt.Color.blue);
		graphics.fillRect(0, 0, 30, 30);
		spellSprites.put("14789", SwingFXUtils.toFXImage(image, null));
	}

	private void drawPlayer(GamePlayer player)
	{
		graphics.drawImage(playerSprite,
				player.getX() + (player.isFaceLeft() ? player.getWidth() / 2 : player.getWidth() / -2),
				player.getY() - player.getHeight() / 2, player.isFaceLeft() ? -player.getWidth() : player.getWidth(),
				player.getHeight());
		graphics.setFont(new Font(20));
		graphics.setFill(Color.BLACK);
		graphics.fillText("" + player.getLives(), player.getX(), player.getY());
	}

	public void draw(GameObject obj)
	{
		if (obj instanceof Spell)
		{
			drawSpell((Spell) obj);
		}
		else if (obj instanceof GamePlayer)
		{
			drawPlayer((GamePlayer) obj);
		}
		else if (obj instanceof BaseRune)
		{
			drawRune((BaseRune) obj);
		}
		else if (obj instanceof Drawable)
		{
			((Drawable) obj).drawOn(graphics);
		}
	}

	private void drawRune(BaseRune rune)
	{
		graphics.drawImage(runeSprites.get(rune.getLong()), rune.getX(), rune.getY(), 30, 30);
	}

	private void drawSpell(Spell spell)
	{
		String s = "";

		for (long l : spell.getSpellCombo())
		{
			s += l;
		}

		graphics.drawImage(spellSprites.get(s), spell.getX(), spell.getY(), 30, 30);
	}
}
