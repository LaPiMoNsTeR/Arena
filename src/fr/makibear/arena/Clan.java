package fr.makibear.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Clan 
{
	private static File cFile;
	private static FileConfiguration cConfig;
	
	private static File pFile;
	private static FileConfiguration pConfig;
	
	public static boolean PLAYER_CAN_CHANGE_CLAN;
	
	static
	{
		cFile = new File(ArenaPlugin.getInstance().getDataFolder().getPath()+"/config.yml");
		cConfig = YamlConfiguration.loadConfiguration(cFile);
		
		if(cFile.exists() == false)
			ArenaPlugin.getInstance().saveDefaultConfig();
		
		PLAYER_CAN_CHANGE_CLAN = cConfig.getBoolean("player-can-change-clan");
		
		pFile = new File(ArenaPlugin.getInstance().getDataFolder().getPath()+"/players.yml");
		pConfig = YamlConfiguration.loadConfiguration(pFile);
		
		if(pFile.exists() == false)
		{
			try 
			{
				pFile.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private int id;
	private String name;
	private int point;
	
	private List<String> all_players = new ArrayList<String>();
	private List<Player> online_players = new ArrayList<Player>();
	
	private static ArrayList<Clan> clans = new ArrayList<Clan>();
	
	public Clan(int id) 
	{
		clans.add(this);
		
		this.id = id;
		
		if(cConfig.getString("clans."+this.id+".name") != null)
			this.name = cConfig.getString("clans."+this.id+".name");
		
		if(pConfig.get(this.id+"") == null)
		{
			pConfig.createSection(this.id+"");
			this.saveUsers();
		}
		
		this.point = ArenaPlugin.getInstance().getSQL().getClanPoint(this);
		
		this.all_players = pConfig.getStringList(this.id+"");
	}
	
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getPoint()
	{
		return this.point;
	}
	
	
	public void setPoint(int point)
	{
		this.point = point;
	}
	
	
	
	public void add(Player p)
	{
		this.all_players.add(p.getName());
		this.online_players.add(p);
		p.sendMessage("Vous avez rejoint le clan "+this.getName()+".");
		this.saveUsers();
	}
	
	public void kick(Player p)
	{
		this.all_players.remove(p.getName());
		this.online_players.remove(p);
		p.sendMessage("Vous avez quitté le clan "+this.getName()+".");
		this.saveUsers();
	}
	
	public boolean isIn(Player p)
	{
		return this.online_players.contains(p.getName());
	}
	
	
	public void connect(Player p)
	{
		this.online_players.add(p);
	}
	
	public void disconnect(Player p)
	{
		this.online_players.remove(p);
	}
	
	
	private void saveUsers()
	{
		pConfig.set(this.id+"", this.all_players);
		this.saveUsersConfig();
	}

	private void saveUsersConfig()
	{
		try 
		{
			pConfig.save(pFile);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<String> getAllPlayers()
	{
		return (ArrayList<String>) this.all_players;
	}
	
	public ArrayList<Player> getOnlinePlayers()
	{
		return (ArrayList<Player>) this.online_players;
	}
	
	
	public static ArrayList<Clan> getClans()
	{
		return clans;
	}
	
}
