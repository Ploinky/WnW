package de.jjl.wnw.dev.spell;

import java.util.*;

import de.jjl.wnw.dev.game.Player;

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
		SpellFactory spell = new SpellFactory("test_spell", 1, new long[] {753});
		SpellFactory spell1 = new SpellFactory("test_spell1", 2, new long[] {159});
		SpellFactory spell2 = new SpellFactory("test_spell2", 3, new long[] {14789});
		spells.addAll(Arrays.asList(spell, spell1, spell2));
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
