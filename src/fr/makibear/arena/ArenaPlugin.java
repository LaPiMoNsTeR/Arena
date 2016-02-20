package fr.makibear.arena;

import org.bukkit.plugin.java.JavaPlugin;

public class ArenaPlugin extends JavaPlugin
{
	private static ArenaPlugin instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
	}
	
	public static ArenaPlugin getInstance()
	{
		return instance;
	}
}
