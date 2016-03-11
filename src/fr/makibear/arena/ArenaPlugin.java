package fr.makibear.arena;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.makibear.arena.datas.APlayer;
import fr.makibear.arena.listeners.PlayerBlockListener;
import fr.makibear.arena.listeners.PlayerChatListener;
import fr.makibear.arena.listeners.PlayerDamageListener;
import fr.makibear.arena.listeners.PlayerDeathListener;
import fr.makibear.arena.listeners.PlayerItemListener;
import fr.makibear.arena.listeners.PlayerJoinQuitListener;
import fr.makibear.arena.listeners.PlayerMoveListener;
import fr.makibear.arena.utils.PlayerUtils;

public class ArenaPlugin extends JavaPlugin
{
	private static ArenaPlugin instance;
	
	private Mysql sql;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		loadCommands();
		loadEvents();
		loadSQL();
		loadClans();
		loadArenas();
		loadOnlinePlayers();
		
		for(Player p : Bukkit.getServer().getOnlinePlayers())
			new APlayer(p);
	}
	
	private void loadCommands()
	{
		this.getCommand("arena").setExecutor(new ArenaCommand());
		this.getCommand("clan").setExecutor(new ClanCommands());
		this.getCommand("duel").setExecutor(new DuelCommand());
	}
	
	private void loadEvents()
	{
		this.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBlockListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerItemListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		
		this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
	}

	private void loadClans()
	{
		new Clan(1);
		new Clan(2);
	}
	
	private void loadOnlinePlayers()
	{
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			System.out.println(PlayerUtils.inClan(p.getName()));
			if(PlayerUtils.inClan(p.getName()))
				PlayerUtils.getClan(p.getName()).connect(p);
		}
	}
	
	private void loadArenas()
	{
		File folder = new File(Arena.FOLDER_PATH);
		if(folder.exists() == false)
			folder.mkdirs();
		for(String f : folder.list())
			new Arena(f.replace(".yml", ""));
	}
	
	private void loadSQL()
	{
		this.sql = new Mysql();
	}

	
	public Mysql getSQL()
	{
		return this.sql;
	}
	
	
	public static ArenaPlugin getInstance()
	{
		return instance;
	}
}
