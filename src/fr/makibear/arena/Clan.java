package fr.makibear.arena;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Clan 
{
	private static File file;
	private static FileConfiguration config;
	
	static
	{
		file = new File(ArenaPlugin.getInstance().getDataFolder().getPath()+"/config.yml");
		config = YamlConfiguration.loadConfiguration(file);
		
		if(file.exists() == false)
			ArenaPlugin.getInstance().saveDefaultConfig();
	}
	
	private int id;
	private String name;
	
	private static ArrayList<Clan> clans = new ArrayList<Clan>();
	
	public Clan(int id) 
	{
		clans.add(this);
		
		this.id = id;
		
		if(config.getString("clans."+this.id+".name") != null)
			this.name = config.getString("clans."+this.id+".name");
	}
	
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	
	
	public static ArrayList<Clan> getClans()
	{
		return clans;
	}
	
}
