package fr.makibear.arena.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.makibear.arena.utils.PlayerUtils;

public class PlayerDamageListener implements Listener
{
	@EventHandler
	public void onPlayerDamagePlayer(EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player)
		{
			Player s = (Player) e.getDamager(), r = (Player) e.getEntity();
			if(PlayerUtils.inClan(s) && PlayerUtils.inClan(r))
			{
				if(PlayerUtils.getClan(s) == PlayerUtils.getClan(r))
					e.setCancelled(true);
			}
		}
	}
}
