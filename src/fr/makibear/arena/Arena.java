package fr.makibear.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
	private Location end;
	
	private static ArrayList<Arena> arenas = new ArrayList<Arena>();
	
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
			this.spawn[0] = Location.deserialize(this.config.getConfigurationSection(this.name+".spawn.1").getValues(false));
		if(this.config.get(this.name+".spawn.2") != null)
			this.spawn[1] = Location.deserialize(this.config.getConfigurationSection(this.name+".spawn.2").getValues(false));
		if(this.config.get(this.name+".end") != null)
			this.end 	  = Location.deserialize(this.config.getConfigurationSection(this.name+".end").getValues(false));
	}
	
	public void delete()
	{
		if(this.file.delete() == false)
			this.file.deleteOnExit();
		arenas.remove(this);
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
	
	public Location getEnd()
	{
		return this.end;
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
		if(this.spawn[0] != null) this.config.set(this.name+".spawn.1", this.spawn[0].serialize());
		if(this.spawn[1] != null) this.config.set(this.name+".spawn.2", this.spawn[1].serialize());
		this.save();
	}
	
	public void setEnd(Location l)
	{
		this.end = l;
		this.config.set(this.name+".end", this.end.serialize());
		this.save();
	}
	
	
	public boolean isGood()
	{
		return this.spawn[0] != null && this.spawn[1] != null && this.end != null && this.type != null;
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
		v1,
		v2,
		v3,
		v4;
		
		public int getMaxPlayers()
		{
			return Integer.parseInt(super.toString().substring(1))*2;
		}
		
		public int getMaxPlayersSameClan()
		{
			return getMaxPlayers()/2;
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
		
		@Override
		public String toString() 
		{
			String n = super.toString().substring(1);
			return n+"v"+n;
		}
	}
}
