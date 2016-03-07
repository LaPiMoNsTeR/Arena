package fr.makibear.arena.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.makibear.arena.Clan;
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
	
	public static ArrayList<Player> getPlayerInClan(Duel d, Clan c)
	{
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : c.getOnlinePlayers())
			if(d.getPlayers().contains(p))
				players.add(p);
		return players;
	}
}
