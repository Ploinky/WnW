/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.dev.rune;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.jjl.wnw.base.util.WnWMap;

public class LoadRunes
{
	private LoadRunes()
	{
		
	}
	
	public static void load(RuneFactory factory)
	{
		List<String> content = new ArrayList<>();
		try
		{
			content = Files.readAllLines(new File("runes.wnw").toPath());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s : content)
		{
			WnWMap map = new WnWMap(s);
			
			// TODO $Li 20.09.2017 MAGIC STRINGS
			int damage = Integer.parseInt(map.get("Damage"));
			long l = Long.valueOf(map.get("Long"));

			Runes.addRuneLong(l, factory.cre(l));
		}
	}
}
