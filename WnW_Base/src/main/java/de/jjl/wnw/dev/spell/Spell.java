package de.jjl.wnw.dev.spell;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.dev.game.GameObject;
import de.jjl.wnw.dev.game.Player;

public class Spell implements GameObject
{
	private static long spellCount = 0;
	
	private static long generateSpellId()
	{
		return spellCount++;
	}
	
	private Player caster;

	private int damage;

	private int height;

	private String name;

	private boolean shield;

	private int speed;

	private long[] spellCombo;

	private int width;

	private volatile double x;

	private double y;
	
	private long castTime;
	
	private long id;

	public long getId()
	{
		return id;
	}

	public Spell(Player player, String name, int damage, long[] spellCombo, boolean shield)
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
		speed = Const.SPELL_SPEED;
		id = generateSpellId();

		this.setCaster(player);
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
		return (int) x;
	}

	@Override
	public int getY()
	{
		return (int) y;
	}

	public void hit()
	{
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
			if(caster.isFaceLeft())
			{
				x -= frameTime * speed / 1000;
			}
			else
			{
				x += frameTime * speed / 1000;
			}
		}
		else
		{
			this.x = getCaster().getX();
			
			if(caster.isFaceLeft())
			{
				x -= (getWidth() + getCaster().getWidth());
			}
			else
			{
				x += (getWidth() + getCaster().getWidth()) * 2;
			}
		}
	}

	public void setCaster(Player caster)
	{
		this.caster = caster;
	}

	public void setPos(int x, int y)
	{
		this.x = x;
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

	public void setId(long id)
	{
		this.id = id;
	}
}
