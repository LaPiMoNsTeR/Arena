package fr.makibear.arena;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.makibear.arena.Arena.ArenaType;
import fr.makibear.arena.utils.ArenaUtils;
import fr.makibear.arena.utils.ChatUtils;

public class ArenaCommand implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender instanceof Player == false) return true;
		
		Player p = (Player) sender;
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("list"))
			{
				String arenas = "Arènes : ";
				for(Arena a : Arena.getArenas())
					arenas += (a.isGood() ? "§a" : "c")+a.getName()+"("+a.getType().toString()+"), ";
				arenas = arenas.substring(0, arenas.length()-2)+".";
				p.sendMessage(arenas);
			}
		}
		else if(args.length < 2)
		{
			p.sendMessage("[COMMAND] /arena <name> <action>");
			p.sendMessage("[ACTION] create, setspawn1, setspawn2, setend, settype, isgood");
		}
		else
		{
			if(args[1].equalsIgnoreCase("create"))
			{
				if(ArenaUtils.getByName(args[0]) == null)
				{
					Arena a = new Arena(args[0]);
					ChatUtils.info("Arène "+a.getName()+" créé.", p, Bukkit.getConsoleSender());
				}
				else ChatUtils.severe("Cette arène existe déjà.", p);
				return true;
			}
			else if(ArenaUtils.getByName(args[0]) == null)
			{
				ChatUtils.severe("Cette arène n'existe pas.", p);
				return true;
			}
			
			
			Arena a = ArenaUtils.getByName(args[0]);
			
			if(args[1].equalsIgnoreCase("delete"))
			{
				a.delete();
				ChatUtils.info("Arène "+a.getName()+" supprimé.", p, Bukkit.getConsoleSender());
			}
			else if(args[1].equalsIgnoreCase("setspawn1"))
			{
				a.setSpawn(0, p.getLocation());
				ChatUtils.info("Spawn 1 de l'arène "+a.getName()+" définie.", p, Bukkit.getConsoleSender());
			}
			else if(args[1].equalsIgnoreCase("setspawn2"))
			{
				a.setSpawn(1, p.getLocation());
				ChatUtils.info("Spawn 2 de l'arène "+a.getName()+" définie.", p, Bukkit.getConsoleSender());
			}
			else if(args[1].equalsIgnoreCase("setend"))
			{
				a.setEnd(p.getLocation());
				ChatUtils.info("Location de fin de duel de l'arène "+a.getName()+" définie.", p, Bukkit.getConsoleSender());
			}
			else if(args[1].equalsIgnoreCase("settype"))
			{
				if(args.length < 3)
				{
					ChatUtils.severe("[COMMAND] /arena <name> settype <type>", p);
					ChatUtils.severe("[TYPE] 1v1, 2v2, 3v3, 4v4", p);
				}
				else if(ArenaType.getByName(args[2]) == null)
					ChatUtils.severe("Type d'arène inconue.", p);
				else
				{
					a.setType(ArenaType.getByName(args[2]));
					ChatUtils.info("Type de l'arène "+a.getName()+" définie.", p, Bukkit.getConsoleSender());
				}
			}
			else if(args[1].equalsIgnoreCase("isgood"))
			{
				if(a.isGood())
					p.sendMessage("L'arène "+a.getName()+" est configuré.");
				else p.sendMessage("L'arène "+a.getName()+" est mal configuré.");
			}
			else
			{
				p.sendMessage("[COMMAND] /arena <name> <action>");
				p.sendMessage("[ACTION] create, setspawn1, setspawn2, setend, settype, isgood");
			}
		}
		return true;
	}
}
