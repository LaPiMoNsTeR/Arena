package fr.makibear.arena.utils;

import org.bukkit.entity.Player;
import fr.makibear.arena.Duel;

public class DuelUtils 
{
	public static Duel getByPlayer(Player p)
	{
		for(Duel duel : Duel.getDuels())
		{
			if(duel.getPlayers().contains(p))
				return duel;
		}
		return null;
	}
}
