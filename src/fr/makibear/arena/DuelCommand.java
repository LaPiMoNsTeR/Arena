package fr.makibear.arena;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.makibear.arena.Arena.ArenaType;
import fr.makibear.arena.utils.ArenaUtils;
import fr.makibear.arena.utils.PlayerUtils;
import fr.makibear.arena.utils.WLUtils;

public class DuelCommand implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player == false) return true;
		
		Player p = (Player) sender;

		if(PlayerUtils.inClan(p) == false)
		{
			p.sendMessage("Vous devez d'abord rejoindre un clan.");
		}
		else if(args.length == 0)
		{
			p.sendMessage("[COMMAND] /duel <action>");
			p.sendMessage("[ACTION] join [1v1 / 2v2 / 3v3 / 4v4], leave");
		}
		else if(args[0].equalsIgnoreCase("i"))
		{
			p.sendMessage(PlayerUtils.inDuel(p)+"");
		}
		else if(args[0].equalsIgnoreCase("join"))
		{
			if(args.length == 1)
				p.sendMessage("[COMMAND] /duel join [1v1 / 2v2 / 3v3 / 4v4]");
			else if(WLUtils.isIn(p) == false)
			{
				ArenaType type;
				if((type = ArenaType.getByName(args[1])) != null)
				{
					if(ArenaUtils.getByType(type).size() == 0)
					{
						p.sendMessage("Aucune arène "+type.toString()+" n'est disponible.");
						return true;
					}
					WaitingLine wl;
					if((wl = WLUtils.get(type)) == null)
					{
						Bukkit.broadcastMessage("new");
						wl = new WaitingLine(ArenaType.getByName(args[1]));
						wl.add(p);
						Bukkit.broadcastMessage(p.getName()+" a boot un waiting line "+wl.getType().toString());
					}
					else 
					{
						Bukkit.broadcastMessage("exist");
						wl.add(p);
					}
				}
				else p.sendMessage("Ce type d'arène n'existe pas.");
			}
			else p.sendMessage("Vous êtes déjà dans une file d'attente.");
		}
		else if(args[0].equalsIgnoreCase("leave"))
		{
			if(WLUtils.isIn(p))
				WLUtils.getByPlayer(p).kick(p);
			else p.sendMessage("Vous n'êtes dans aucune file d'attente.");
		}
		else 
		{
			p.sendMessage("[COMMAND] /duel <action>");
			p.sendMessage("[ACTION] join [1vs1 / 2vs2 / 3vs3 / 4vs4], leave");
		}
		return true;
	}
}
