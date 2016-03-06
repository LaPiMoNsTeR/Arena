package fr.makibear.arena;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.makibear.arena.Arena.ArenaType;
import fr.makibear.arena.utils.ArenaUtils;
import fr.makibear.arena.utils.ChatUtils;
import fr.makibear.arena.utils.ClanUtils;

public class WaitingLine 
{
	private int id;
	private ArenaType type;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private static ArrayList<WaitingLine> wls = new ArrayList<WaitingLine>();
	
	public WaitingLine(ArenaType type) 
	{
		wls.add(this);
		this.id = wls.size();
		this.type = type;
	}
	
	public void delete()
	{
		wls.remove(this);
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public ArenaType getType()
	{
		return this.type;
	}

	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	
	
	@SuppressWarnings("unchecked")
	public void add(Player p)
	{
		//Bukkit.broadcastMessage(this.type.toString()+"("+this.id+")");
		
		//Bukkit.broadcastMessage("0 : "+this.players.toString());
		ArrayList<Player> pGood = (ArrayList<Player>) this.players.clone();
		//Bukkit.broadcastMessage("0 pGood : "+this.players.toString());
		pGood.add(p);
		//Bukkit.broadcastMessage("0 pClan : "+PlayerUtils.getClan(p).getOnlinePlayers().toString());
		
		Clan ad = null;
		for(Clan c : Clan.getClans())
		{
			//Bukkit.broadcastMessage(c.getName()+"");
			if(c.getOnlinePlayers().contains(p) == false)
			{
				//Bukkit.broadcastMessage("true");
				ad = c;
				break;
			}
		}
		
		/*for(Player t : ad.getOnlinePlayers())
		{
			for(Player tt : pGood)
			{
				//Bukkit.broadcastMessage(tt.getName()+" == "+t.getName());
				if(t.equals(tt))
					pGood.remove(tt);
			}
		}*/
		
		pGood.removeAll(ad.getOnlinePlayers());
		
		//Bukkit.broadcastMessage("1 pGood : "+pGood.toString());
		
		//Bukkit.getLogger().info((pGood.size()+1)+" <= "+this.type.getMaxPlayersSameClan()+" = "+((pGood.size()+1) <= this.type.getMaxPlayersSameClan()));
		
		if(pGood.size() <= this.type.getMaxPlayersSameClan())
		{
			//Bukkit.broadcastMessage("true");
			this.players.add(p);
			//Bukkit.broadcastMessage("1 : "+this.players.toString());
			ChatUtils.info("Vous avez rejoint une file d'attente pour un match "+type.toString(), p);

			//Bukkit.broadcastMessage(this.players.size()+" == "+this.type.getMaxPlayers()+" = "+(this.players.size() == this.type.getMaxPlayers()));
			if(this.players.size() == this.type.getMaxPlayers())
			{
				//Bukkit.broadcastMessage("in duel booter");
				for(Arena a : ArenaUtils.getByType(this.type))
				{
					if(a.isGood())
					{
						this.boot(a);
						return;
					}
				}
			}
			
			//Bukkit.broadcastMessage("2 : "+this.players.toString());
			ArrayList<Player> cP1 = (ArrayList<Player>) this.players.clone(), cP2 = (ArrayList<Player>) this.players.clone();
			cP1.removeAll(ClanUtils.getById(2).getOnlinePlayers());
			cP2.removeAll(ClanUtils.getById(1).getOnlinePlayers());
			//Bukkit.broadcastMessage("3 : "+this.players.toString());
			int c1 = this.type.getMaxPlayersSameClan() - cP1.size();
			int c2 = this.type.getMaxPlayersSameClan() - cP2.size();
			//Bukkit.broadcastMessage("4 : "+this.players.toString());
			Player[] sT = this.players.toArray(new Player[this.players.size()]);
			if(c1 > 0) ChatUtils.sendMessage("Il manque "+c1+" joueur"+(c1>1?"s":"")+" du clan "+ClanUtils.getById(1).getName()+".", sT);
			if(c2 > 0) ChatUtils.sendMessage("Il manque "+c2+" joueur"+(c2>1?"s":"")+" du clan "+ClanUtils.getById(2).getName()+".", sT);
			
			//Bukkit.broadcastMessage("5 : "+this.players.toString());
			//Bukkit.broadcastMessage("isIn: "+this.players.contains(p));
			
			//Bukkit.broadcastMessage(" ");
		}
		else
		{
			//Bukkit.broadcastMessage("false");
			WaitingLine wl = new WaitingLine(this.type);
			wl.add(p);
		}
	}
	
	public void kick(Player p)
	{
		this.players.remove(p);
		p.sendMessage("Vous avez quitté votre file d'attente.");
		
		if(this.players.size() == 0)
			this.delete();
	}
	
	
	public void boot(Arena a)
	{
		new Duel(a, this.players);
	}
	
	
	public static ArrayList<WaitingLine> getWaitingLines()
	{
		return wls;
	}
}
