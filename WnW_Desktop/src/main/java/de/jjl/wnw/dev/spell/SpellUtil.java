package de.jjl.wnw.dev.spell;

import java.util.ArrayList;
import java.util.List;

public class SpellUtil
{

	private List<Spell> spells;

	private static SpellUtil instance;

	private static SpellUtil getInstance()
	{
		if (instance == null)
		{
			instance = new SpellUtil();
		}

		return instance;
	}

	private SpellUtil()
	{
		spells = new ArrayList<>();

		load();
	}

	private void load()
	{
		Spell spell = new Spell("test_spell", new long[]
		{ 753, 159, 14789 });
		spells.add(spell);
	}

	public static Spell getRune(long[] combo)
	{
		for (Spell s : getInstance().spells)
		{
			if (s.getSpellCombo().length != combo.length)
			{
				continue;
			}

			boolean spell = true;

			for (int i = 0; i < s.getSpellCombo().length; i++)
			{
				if (s.getSpellCombo()[i] != combo[i])
				{
					spell = false;
					;
				}
			}

			if (spell)
			{
				return s;
			}
		}

		return null;
	}
}
