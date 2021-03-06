package de.jjl.wnw.dev.spell;

import de.jjl.wnw.dev.game.Player;

public class SpellFactory
{
	private String name;

	private long[] combo;

	private int damage;

	public SpellFactory(String name, int damage, long[] combo)
	{
		this.name = name;
		this.damage = damage;
		this.combo = combo;
	}

	public Spell cre(Player player, boolean shield)
	{
		return new Spell(player, name, damage, combo, shield);
	}

	public long[] getSpellCombo()
	{
		return combo;
	}
}
