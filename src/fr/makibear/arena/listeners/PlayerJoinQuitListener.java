package fr.makibear.arena.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.makibear.arena.utils.PlayerUtils;

public class PlayerJoinQuitListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		System.out.println(PlayerUtils.inClan(e.getPlayer().getName()));
		if(PlayerUtils.inClan(e.getPlayer().getName()))
			PlayerUtils.getClan(e.getPlayer().getName()).connect(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if(PlayerUtils.inClan(e.getPlayer()))
			PlayerUtils.getClan(e.getPlayer()).disconnect(e.getPlayer());
	}
}
