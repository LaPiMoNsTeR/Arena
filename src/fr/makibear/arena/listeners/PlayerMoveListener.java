package fr.makibear.arena.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.makibear.arena.utils.PlayerUtils;

public class PlayerMoveListener implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		if(PlayerUtils.inDuel(e.getPlayer()))
		{
			if(PlayerUtils.getDuel(e.getPlayer()).getPrestart() != null)
				e.getPlayer().teleport(e.getFrom());
		}
	}
}
