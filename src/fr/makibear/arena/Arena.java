package fr.makibear.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Arena 
{
	public static String FOLDER_PATH = ArenaPlugin.getInstance().getDataFolder().getPath()+"/Arenas";
	
	static
	{
		if(new File(FOLDER_PATH).exists() == false)
			new File(FOLDER_PATH).mkdir();
	}
	
	private File file;
	private FileConfiguration config;
	
	private int id;
	private String name;
	private ArenaType type = null;
	private Location[] spawn = new Location[2];
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private static ArrayList<Arena> arenas = new ArrayList<>();
	
	public Arena(String name) 
	{
		arenas.add(this);
		this.id = arenas.size();
		this.name = name;
		
		this.file = new File(FOLDER_PATH+"/"+this.name+".yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
		
		if(this.file.exists() == false)
		{
			try 
			{
				this.file.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		this.type = ArenaType.getByName(this.config.getString(this.name+".type"));
		
		if(this.config.get(this.name+".spawn.1") != null)
		{
			this.spawn[0] = Location.deserialize(this.config.getConfigurationSection(this.name+".spawn.1").getValues(false));
		}
		if(this.config.get(this.name+".spawn.2") != null)
		{
			this.spawn[1] = Location.deserialize(this.config.getConfigurationSection(this.name+".spawn.2").getValues(false));
		}
	}
	
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ArenaType getType()
	{
		return this.type;
	}
	
	public Location getSpawn(int i)
	{
		return this.spawn[i];
	}
	
	
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	
	
	
	public void setType(ArenaType type)
	{
		this.type = type;
		this.config.set(this.name+".type", this.type.toString());
		this.save();
	}
	
	public void setSpawn(int i, Location l)
	{
		this.spawn[i] = l;
		this.config.set(this.name+".spawn.1", this.spawn[0].serialize());
		this.config.set(this.name+".spawn.2", this.spawn[1].serialize());
		this.save();
	}
	
	
	public boolean isGood()
	{
		return this.spawn[0] != null && this.spawn[1] != null && this.type != null;
 	}
	
	
	public void save()
	{
		try 
		{
			this.config.save(this.file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	public static ArrayList<Arena> getArenas()
	{
		return arenas;
	}
	
	
	
	public enum ArenaType
	{
		v1(2),
		v2(4),
		v3(6),
		v4(8);
		
		private int maxP;
		
		private ArenaType(int maxP) 
		{
			this.maxP = maxP;
		}
		
		public int getMaxPlayer()
		{
			return this.maxP;
		}
		
		
		public static ArenaType getByName(String name)
		{
			for(ArenaType at : values())
			{
				if(at.toString().equalsIgnoreCase(name))
					return at;
			}
			return null;
		}
	}
}
