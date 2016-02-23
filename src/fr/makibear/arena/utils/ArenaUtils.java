package fr.makibear.arena.utils;

import java.util.ArrayList;

import fr.makibear.arena.Arena;
import fr.makibear.arena.Arena.ArenaType;

public class ArenaUtils 
{
	public static ArrayList<Arena> getByType(ArenaType type)
	{
		ArrayList<Arena> aL = new ArrayList<Arena>();
		for(Arena a : Arena.getArenas())
		{
			if(a.getType() == type)
				aL.add(a);
		}
		return aL;
	}
}
