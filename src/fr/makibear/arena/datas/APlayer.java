package fr.makibear.arena.datas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.makibear.arena.ArenaPlugin;

public class APlayer 
{
	private static ArrayList<APlayer> aplayers = new ArrayList<APlayer>();
	private UUID uuid;
	private int win = 0, loose = 0, xp = 0;
	
	public APlayer(Player p) 
	{
		aplayers.add(this);
		this.uuid = p.getUniqueId();
		
		ResultSet r = ArenaPlugin.getInstance().getSQL().read("SELECT * FROM duel_stats", new Object[0]);
		ResultSet rXP = ArenaPlugin.getInstance().getSQL().read("SELECT * FROM duel_player WHERE player=?", new Object[] {p.getName()});
		try 
		{
			while(r.next())
			{
				String[] players_c1 = r.getString("clan1").split(",");
				List<String> p_l_c1 = Arrays.asList(players_c1);
				String[] players_c2 = r.getString("clan2").split(",");
				List<String> p_l_c2 = Arrays.asList(players_c2);
				if(p_l_c1.contains(this.getPlayer().getName()))
				{
					if(r.getInt("winner") == 1)
					{
						this.win = this.win + 1;
					}
					else if(r.getInt("winner") == 2) 
					{
						this.loose = this.loose + 1;
					}
				}
				else if(p_l_c2.contains(this.getPlayer().getName()))
				{
					if(r.getInt("winner") == 2)
					{
						this.win = this.win + 1;
					}
					else if(r.getInt("winner") == 1) 
					{
						this.loose = this.loose + 1;
					}
				}
				
				boolean b = false;
				while(rXP.next())
					b = true;
				if(b)
					this.xp = rXP.getInt("xp");
				else
				{
					this.xp = 0;
					ArenaPlugin.getInstance().getSQL().registerPlayer(this);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void delete()
	{
		aplayers.remove(this);
	}
	
	
	public UUID getUUID()
	{
		return this.uuid;
	}
	
	public Player getPlayer()
	{
		return Bukkit.getServer().getPlayer(this.uuid);
	}
	
	public int getWin()
	{
		return this.win;
	}
	
	public int getLoose()
	{
		return this.loose;
	}
	
	public int getXp()
	{
		return this.xp;
	}
	
	public void setWin(int i)
	{
		this.win = i;
	}
	
	public void setLoose(int i)
	{
		this.loose = i;
	}
	
	public void setXp(int i)
	{
		this.xp = i;
		if(this.xp < 0)
			this.xp = 0;
		ArenaPlugin.getInstance().getSQL().savePlayer(this);
	}
	
	
	public static APlayer get(Player p)
	{
		for(APlayer ap : aplayers)
		{
			if(ap.getPlayer() == p)
				return ap;
		}
		return null;
	}
}
