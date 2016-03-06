package fr.makibear.arena.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ChatUtils 
{
	public static void info(String message, CommandSender... senders)
	{
		for(CommandSender sender : senders)
		{
			if(sender instanceof ConsoleCommandSender) Bukkit.getServer().getLogger().info(message);
			else if(sender instanceof Player) sender.sendMessage("§7"+message);
		}
	}
	
	public static void severe(String message, CommandSender... senders)
	{
		for(CommandSender sender : senders)
		{
			if(sender instanceof ConsoleCommandSender) Bukkit.getServer().getLogger().severe(message);
			else if(sender instanceof Player) sender.sendMessage("§c"+message);
		}
	}
	
	public static void sendMessage(String message, Player... players)
	{
		for(Player p : players)
			p.sendMessage(message);
	}
}
