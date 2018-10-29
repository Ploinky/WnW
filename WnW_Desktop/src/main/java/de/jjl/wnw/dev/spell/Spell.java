package de.jjl.wnw.dev.spell;

import de.jjl.wnw.dev.game.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class Spell extends GameObject
{
	private long[] spellCombo;

	private String name;

	private int width;

	private int height;

	private double x;

	private double y;

	private int speed;

	public Spell(String name, long[] spellCombo)
	{
		this.name = name;
		this.spellCombo = spellCombo;
		width = 30;
		height = 30;
		x = 0;
		y = 0;
		speed = 100;
	}

	public void setPos(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public long[] getSpellCombo()
	{
		return spellCombo;
	}

	public Spell creNew()
	{
		return new Spell(name, spellCombo);
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void move(float frameTime)
	{
		x += frameTime * speed / 100000000;
	}
}
