package de.jjl.wnw.dev.spell;

import de.jjl.wnw.desktop.game.DesktopPlayer;
import de.jjl.wnw.dev.game.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class Spell extends GameObject
{
	private long[] spellCombo;

	private DesktopPlayer caster;

	private String name;

	private int width;

	private int height;

	private int x;

	private int y;

	private int speed;

	private int damage;

	private boolean hit;

	public Spell(DesktopPlayer caster, String name, int damage, long[] spellCombo)
	{
		this.name = name;
		this.spellCombo = spellCombo;
		this.damage = damage;
		width = 30;
		height = 30;
		x = 0;
		y = 0;
		speed = 100;
		hit = false;

		this.setCaster(caster);
	}

	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public long[] getSpellCombo()
	{
		return spellCombo;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		if (!hit)
		{
			graphics.fillRect(x, y, width, height);
		}
		else
		{
			graphics.fillOval(x, y, width, height);
		}
	}

	@Override
	public void move(float frameTime)
	{
		x += frameTime * speed / 100000000;
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
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public DesktopPlayer getCaster()
	{
		return caster;
	}

	public void setCaster(DesktopPlayer caster)
	{
		this.caster = caster;
	}

	public int getDamage()
	{
		return damage;
	}

	public void hit()
	{
		hit = true;
		damage = 0;
		speed = 0;
	}
}
