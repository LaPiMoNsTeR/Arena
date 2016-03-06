package fr.makibear.arena.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.makibear.arena.utils.PlayerUtils;

public class PlayerChatListener implements Listener
{
	@EventHandler
	public void onPlayerSpeak(AsyncPlayerChatEvent e)
	{
		if(PlayerUtils.inClan(e.getPlayer()))
		{
			e.setFormat("["+PlayerUtils.getClan(e.getPlayer()).getName()+"]"+e.getFormat());
		}
	}
}
