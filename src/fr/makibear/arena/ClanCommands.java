package fr.makibear.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.makibear.arena.utils.ClanUtils;
import fr.makibear.arena.utils.PlayerUtils;

public class ClanCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player == false) return true;
		
		Player p = (Player) sender;
		if(args.length == 0)
		{
			p.sendMessage("[COMMAND] /clan <action>");
			p.sendMessage("[ACTION] join [CLAN], leave");
		}
		else if(args[0].equalsIgnoreCase("join"))
		{
			Clan c;
			if(args.length == 1)
				p.sendMessage("/clan join [CLAN]");
			else if((c = ClanUtils.getByName(args[1])) == null)
			{
				p.sendMessage("Ce clan n'existe pas.");
			}
			else if(PlayerUtils.inClan(p))
			{
				if(Clan.PLAYER_CAN_CHANGE_CLAN)
				{
					if(c.isIn(p))
						p.sendMessage("Vous êtes déjà dans ce clan.");
					else c.add(p);
				}
				else p.sendMessage("Vous ne pouvez pas changer de clan.");
			}
			else c.add(p);
		}
		else if(args[0].equalsIgnoreCase("leave"))
		{
			if(Clan.PLAYER_CAN_CHANGE_CLAN)
			{
				if(PlayerUtils.inClan(p))
					PlayerUtils.getClan(p).kick(p);
				else p.sendMessage("Vous n'êtes dans aucun clan.");
			}
			else p.sendMessage("Vous ne pouvez pas changer de clan.");
		}
		return true;
	}
}
