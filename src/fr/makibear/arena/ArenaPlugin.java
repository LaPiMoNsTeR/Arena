package fr.makibear.arena;

import org.bukkit.plugin.java.JavaPlugin;

public class ArenaPlugin extends JavaPlugin
{
	private static ArenaPlugin instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		loadClans();
	}
	

	private void loadClans()
	{
		new Clan(1);
		new Clan(2);
	}
	
	
	
	
	public static ArenaPlugin getInstance()
	{
		return instance;
	}
}
