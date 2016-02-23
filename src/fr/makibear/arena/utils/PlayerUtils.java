package fr.makibear.arena.utils;

import org.bukkit.entity.Player;

import fr.makibear.arena.Arena;

public class PlayerUtils 
{
	public static Arena getArena(Player p)
	{
		for(Arena a : Arena.getArenas())
		{
			if(a.getPlayers().contains(p))
				return a;
		}
		return null;
	}
	
	public static boolean inArena(Player p)
	{
		return getArena(p) != null;
	}
}
