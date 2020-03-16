package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.jjl.wnw.base.consts.Const;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DesktopObjectPainter
{
	private GraphicsContext graphics;

	private Image playerSprite;

	private Map<Long, Image> runeSprites;

	private Map<String, Image> spellSprites;

	private Image mapSprite;

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
		mapSprite = new Image(getClass().getResource("/img/maps/Castle.png").openStream());

		playerSprite = SwingFXUtils
				.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/Lil' Wizzy Cropped.png")), null);

		runeSprites = new HashMap<>();
		// TODO $Li 23.02.2019 boy we REALLY need to fix this!
		runeSprites.put(14789l, SwingFXUtils.toFXImage(
				ImageIO.read(getClass().getResourceAsStream("/img/runes/14789.png")), new WritableImage(40, 40)));
		runeSprites.put(159l, SwingFXUtils.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/runes/159.png")),
				new WritableImage(40, 40)));
		runeSprites.put(753l, SwingFXUtils.toFXImage(ImageIO.read(getClass().getResourceAsStream("/img/runes/753.png")),
				new WritableImage(40, 40)));

		spellSprites = new HashMap<>();

		spellSprites.put("159",
				new Image(getClass().getResource("/img/spells/159.png").openStream(), 500, 500, false, true));
		spellSprites.put("753",
				new Image(getClass().getResource("/img/spells/753.png").openStream(), 500, 500, false, true));
		spellSprites.put("14789",
				new Image(getClass().getResource("/img/spells/14789.png").openStream(), 500, 500, false, true));
		spellSprites.put("159_S",
				new Image(getClass().getResource("/img/spells/159_S.png").openStream(), 500, 500, false, true));
		spellSprites.put("753_S",
				new Image(getClass().getResource("/img/spells/753_S.png").openStream(), 500, 500, false, true));
		spellSprites.put("14789_S",
				new Image(getClass().getResource("/img/spells/14789_S.png").openStream(), 500, 500, false, true));
	}

	private void drawPlayer(GamePlayer player)
	{
		double screenWidth = graphics.getCanvas().getWidth();
		double screenHeight = graphics.getCanvas().getHeight();

		double playerDrawX = (player.getX() / (double) Const.ARENA_WIDTH) * screenWidth;

		// Draw player model
		graphics.drawImage(playerSprite,
				playerDrawX + (player.isFaceLeft() ? player.getWidth() / 2 : player.getWidth() / -2),
				(player.getY() / (double) Const.ARENA_HEIGHT) * screenHeight - player.getHeight() / 2,
				player.isFaceLeft() ? -player.getWidth() : player.getWidth(), player.getHeight());

		graphics.setFont(Font.font("Sans-Serif", FontWeight.BOLD, 30));

		Text t = new Text(player.getName());
		t.setFont(graphics.getFont());
		graphics.setFill(Color.GREEN);
		graphics.fillText(player.getName(), playerDrawX - t.getLayoutBounds().getWidth() / 2,
				(player.getY() / (double) Const.ARENA_HEIGHT) * screenHeight - player.getHeight());

		// Draw healthbar
		graphics.setFill(Color.GREEN);

		double gap = 5;
		double width = screenWidth / 50;
		double height = screenWidth / 50;
		double xCurr = (player.getX() / (double) Const.ARENA_WIDTH) * screenWidth
				- (player.getLives() / 2) * (width + gap);

		for (int i = 0; i < player.getLives(); i++)
		{
			graphics.fillRect(xCurr,
					(player.getY() / (double) Const.ARENA_HEIGHT) * screenHeight + player.getHeight() / 2 + 10, width,
					height);

			xCurr += (width + gap);
		}
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

		double screenWidth = graphics.getCanvas().getWidth();
		double screenHeight = graphics.getCanvas().getHeight();

		// TODO $Li 19.09.2019 Painting, sizing dynamically?!
		if (spell.isShield())
		{
			graphics.drawImage(spellSprites.get(s + "_S"),
					(spell.getX() / (double) Const.ARENA_WIDTH) * screenWidth - spell.getWidth() / 2,
					(spell.getY() / (double) Const.ARENA_HEIGHT) * screenHeight - spell.getHeight() / 2,
					spell.getCaster().isFaceLeft() ? 60 : -60, 100);
		}
		else
		{
			graphics.drawImage(spellSprites.get(s),
					(spell.getX() / (double) Const.ARENA_WIDTH) * screenWidth - spell.getWidth() / 2,
					(spell.getY() / (double) Const.ARENA_HEIGHT) * screenHeight - spell.getHeight() / 2,
					spell.getCaster().isFaceLeft() ? -60 : 60, 60);
		}
	}

	public void drawMap()
	{
		double screenWidth = graphics.getCanvas().getWidth();
		double screenHeight = graphics.getCanvas().getHeight();

		try
		{
			mapSprite = new Image(getClass().getResource("/img/maps/Castle.png").openStream(), screenWidth,
					screenHeight, true, false);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (screenWidth > screenHeight * 2)
		{
			graphics.drawImage(mapSprite, 0, 0, screenWidth, screenWidth / 2);
		}
		else
		{
			graphics.drawImage(mapSprite, 0, 0, screenHeight * 2, screenHeight);
		}
	}
}
