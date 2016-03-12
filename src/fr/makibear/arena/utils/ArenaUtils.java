package fr.makibear.arena.utils;

import java.util.ArrayList;
import java.util.Iterator;

import fr.makibear.arena.Arena;
import fr.makibear.arena.Arena.ArenaType;
import fr.makibear.arena.Duel;

public class ArenaUtils 
{
	public static Arena getByName(String name)
	{
		for(Arena a : Arena.getArenas())
		{
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
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
	
	public static ArrayList<Arena> getGoodByType(ArenaType type)
	{
		ArrayList<Arena> gA = getByType(type);
		Iterator<Arena> iT = gA.iterator();
		while(iT.hasNext())
		{
			Arena a = iT.next();
			if(a.isGood() == false)
				iT.remove();
		}
		return gA;
	}
	
	public static boolean isUsed(Arena a)
	{
		for(Duel duel : Duel.getDuels())
		{
			if(duel.getArena() == a)
				return true;
		}
		return false;
	}
}
