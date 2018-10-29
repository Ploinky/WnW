package de.jjl.wnw.dev.spell;

import java.util.function.Function;

import de.jjl.wnw.desktop.game.DesktopPlayer;

public class SpellFactory implements Function<DesktopPlayer, Spell>
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

	@Override
	public Spell apply(DesktopPlayer caster)
	{
		return new Spell(caster, name, damage, combo);
	}

	public long[] getSpellCombo()
	{
		return combo;
	}
}
