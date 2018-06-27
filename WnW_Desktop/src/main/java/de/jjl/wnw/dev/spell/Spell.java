package de.jjl.wnw.dev.spell;

public class Spell
{
	private long[] spellCombo;

	private String name;

	public Spell(String name, long[] spellCombo)
	{
		this.name = name;
		this.spellCombo = spellCombo;
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
		// TODO Auto-generated method stub
		return name;
	}
}
