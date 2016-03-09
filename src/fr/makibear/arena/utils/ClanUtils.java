package fr.makibear.arena.utils;

import org.bukkit.entity.Player;

import fr.makibear.arena.Clan;

public class ClanUtils 
{
	public static Clan getById(int id)
	{
		for(Clan c : Clan.getClans())
		{
			if(c.getId() == id)
				return c;
		}
		return null;
	}
	
	public static Clan getByName(String name)
	{
		for(Clan c : Clan.getClans())
		{
			if(c.getName().equals(name))
				return c;
		}
		return null;
	}
	
	public static Clan getByPlayer(Player p)
	{
		for(Clan c : Clan.getClans())
		{
			if(c.isIn(p))
				return c;
		}
		return null;
	}
	
	public static Clan getOpposite(Clan c)
	{
		for(Clan t : Clan.getClans())
		{
			if(c != t)
				return t;
		}
		return null;
	}
}
