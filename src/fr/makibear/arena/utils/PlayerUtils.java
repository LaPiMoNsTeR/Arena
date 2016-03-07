package fr.makibear.arena.utils;

import org.bukkit.entity.Player;
import fr.makibear.arena.Clan;
import fr.makibear.arena.Duel;

public class PlayerUtils 
{
	public static Duel getDuel(Player p)
	{
		for(Duel duel : Duel.getDuels())
		{
			if(duel.getPlayers().contains(p))
				return duel;
		}
		return null;
	}
	
	public static boolean inDuel(Player p)
	{
		return getDuel(p) != null;
	}
	
	public static Clan getClan(Player p)
	{
		for(Clan c : Clan.getClans())
		{
			if(c.getOnlinePlayers().contains(p))
				return c;
		}
		return null;
	}

	public static Clan getClan(String p)
	{
		for(Clan c : Clan.getClans())
		{
			if(c.getAllPlayers().contains(p))
				return c;
		}
		return null;
	}
	
	public static boolean inClan(Player p)
	{
		return getClan(p) != null;
	}
	
	public static boolean inClan(String p)
	{
		return getClan(p) != null;
	}
}
