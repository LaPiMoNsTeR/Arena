package fr.makibear.arena.utils;

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
}
