package de.jjl.wnw.dev.spell;

import de.jjl.wnw.dev.game.*;
import javafx.scene.canvas.GraphicsContext;

public class Spell implements GameObject
{
	private Player caster;

	private int damage;

	private int height;

	private boolean hit;

	private String name;

	private boolean shield;

	private int speed;

	private long[] spellCombo;

	private int width;

	private int x;

	private int y;
	
	private long castTime;

	public Spell(Player player1, String name, int damage, long[] spellCombo, boolean shield)
	{
		this.name = name;
		this.spellCombo = spellCombo;
		this.damage = damage;
		this.setShield(shield);
		setCastTime(System.currentTimeMillis());
		width = 30;
		height = shield ? 100 : 30;
		x = 0;
		y = 0;
		speed = 100;
		hit = false;

		this.setCaster(player1);
	}

	public Player getCaster()
	{
		return caster;
	}

	public int getDamage()
	{
		return damage;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public long[] getSpellCombo()
	{
		return spellCombo;
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

	public void hit()
	{
		hit = true;
		damage = 0;
		speed = 0;
	}

	public boolean isShield()
	{
		return shield;
	}

	@Override
	public void update(float frameTime)
	{
		if (!shield)
		{
			x += frameTime * speed / 100000000;
		}
	}

	public void setCaster(Player caster)
	{
		this.caster = caster;
	}

	public void setPos(int x, int y)
	{
		this.x = x + (shield ? (caster.isFaceLeft() ? -30 : 60) : 0);
		this.y = y;
	}

	public void setShield(boolean shield)
	{
		this.shield = shield;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public void weaken(int dmg)
	{
		damage -= dmg;
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
	
	public void setCastTime(long castTime)
	{
		this.castTime = castTime;
	}
	
	public long getCastTime()
	{
		return castTime;
	}
}
