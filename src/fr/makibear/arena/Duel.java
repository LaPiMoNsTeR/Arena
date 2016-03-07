package fr.makibear.arena;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.makibear.arena.utils.ChatUtils;
import fr.makibear.arena.utils.PlayerUtils;

public class Duel 
{
	private Arena arena;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> dplayers = new  ArrayList<Player>();
	private Prestart prestart;
	
	private static ArrayList<Duel> duels = new ArrayList<Duel>();
	
	public Duel(Arena arena, ArrayList<Player> players) 
	{
		duels.add(this);
		
		this.arena = arena;
		this.players = players;
		
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			Clan c = PlayerUtils.getClan(p);
			p.teleport(this.arena.getSpawn(c.getId()-1));
		}
		
		this.prestart = new Prestart();
		this.prestart.runTaskTimer(ArenaPlugin.getInstance(), 0L, 20L);
	}
	
	
	private class Prestart extends BukkitRunnable
	{
		private int cd = 5;
		
		@Override
		public void run() 
		{
			if(this.cd == 0)
			{
				start();
				this.cancel();
			}
			else ChatUtils.sendMessage("Le duel commence dans "+this.cd+" secondes.", players.toArray(new Player[players.size()]));
			this.cd--;
		}
	}
	
	
	private void start()
	{
		this.prestart = null;
		ChatUtils.sendMessage("Le duel a commencé.", players.toArray(new Player[players.size()]));
	}
	
	
	public void end(Clan c)
	{
		ChatUtils.sendMessage("Le clan "+c.getName()+" a gagné le duel.", this.players.toArray(new Player[this.players.size()]));
		ArenaPlugin.getInstance().getSQL().saveDuel(this, c);
		ArrayList<Player> survivant = this.players;
		this.players.removeAll(this.dplayers);
		for(Player p : survivant)
			p.teleport(this.arena.getEnd());
	}
	
	
	
	public Prestart getPrestart()
	{
		return this.prestart;
	}
	
	public Arena getArena()
	{
		return this.arena;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	
	public ArrayList<Player> getDeathPlayers()
	{
		return this.dplayers;
	}
	
	
	
	
	public static ArrayList<Duel> getDuels()
	{
		return duels;
	}
}
