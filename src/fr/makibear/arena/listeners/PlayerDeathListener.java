package fr.makibear.arena.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.makibear.arena.Clan;
import fr.makibear.arena.Duel;
import fr.makibear.arena.utils.ClanUtils;
import fr.makibear.arena.utils.DuelUtils;
import fr.makibear.arena.utils.PlayerUtils;

public class PlayerDeathListener implements Listener
{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(PlayerUtils.inDuel(p))
		{
			Duel d = DuelUtils.getByPlayer(p);
			if(d.getDeathPlayers().contains(p) == false)
			{
				d.getDeathPlayers().add(p);
				e.getDrops().clear();
				e.setDroppedExp(0);
				
				Clan w = getDuelWinner(d);
				
				if(w != null)
					d.end(w);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Clan getDuelWinner(Duel d)
	{
		ArrayList<Player> cP1 = (ArrayList<Player>) d.getPlayers().clone(), cP2 = (ArrayList<Player>) d.getPlayers().clone();
		cP1.removeAll(ClanUtils.getById(2).getOnlinePlayers());
		cP2.removeAll(ClanUtils.getById(1).getOnlinePlayers());
		
		cP1.removeAll(d.getDeathPlayers());
		cP2.removeAll(d.getDeathPlayers());
		
		if(cP1.size() == 0)
			return ClanUtils.getById(2);
		else if(cP2.size() == 0)
			return ClanUtils.getById(1);
		else return null;
	}
}
