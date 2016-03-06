package fr.makibear.arena.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.makibear.arena.Duel;
import fr.makibear.arena.utils.DuelUtils;
import fr.makibear.arena.utils.PlayerUtils;

public class PlayerItemListener implements Listener
{
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e)
	{
		if(e.getPlayer() == null) return;
		
		Player p = e.getPlayer();
		if(PlayerUtils.inDuel(p))
		{
			Duel d = DuelUtils.getByPlayer(p);
			if(d.getDeathPlayers().contains(p) == false)
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e)
	{
		if(e.getPlayer() == null) return;
		
		Player p = e.getPlayer();
		if(PlayerUtils.inDuel(p))
		{
			Duel d = DuelUtils.getByPlayer(p);
			if(d.getDeathPlayers().contains(p) == false)
				e.setCancelled(true);
		}
	}
}
