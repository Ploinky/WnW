package de.jjl.wnw.dev.spell;

import java.util.*;

import de.jjl.wnw.desktop.game.Player;

public class SpellUtil
{

	private List<SpellFactory> spells;

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
		SpellFactory spell = new SpellFactory("test_spell", 1, new long[] { 753, 159, 14789 });
		spells.add(spell);
	}

	public static Spell getSpell(Player player1, List<Long> combo, boolean shield)
	{
		for (SpellFactory s : getInstance().spells)
		{
			if (s.getSpellCombo().length != combo.size())
			{
				continue;
			}

			boolean spell = true;

			for (int i = 0; i < s.getSpellCombo().length; i++)
			{
				if (s.getSpellCombo()[i] != combo.get(i))
				{
					spell = false;
				}
			}

			if (spell)
			{
				return s.cre(player1, shield);
			}
		}

		return null;
	}
}
