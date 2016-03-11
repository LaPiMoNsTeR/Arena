package fr.makibear.arena;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.makibear.arena.datas.APlayer;
import fr.makibear.arena.utils.ClanUtils;
import fr.makibear.arena.utils.DuelUtils;

public class Mysql 
{
	private File file;
	private FileConfiguration config;
	
	private String host;
	private String user;
	private String password;
	private Connection connection;
	
	public Mysql() 
	{
		this.file = new File(ArenaPlugin.getInstance().getDataFolder().getPath()+"/config.yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
		
		if(this.file.exists() == false)
			ArenaPlugin.getInstance().saveDefaultConfig();
		
		this.host = "jdbc:mysql://"+this.config.getString("sql.host")+":"+this.config.getInt("sql.port")+"/"+this.config.getString("sql.db");
		this.user = this.config.getString("sql.user");
		this.password = this.config.getString("sql.password");
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.host, this.user, this.password);
			
			this.init();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			Bukkit.getLogger().severe("Driver introuvable.");
			Bukkit.getLogger().severe("Arrêt du plugin.");
			ArenaPlugin.getInstance().getPluginLoader().disablePlugin(ArenaPlugin.getInstance());
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			Bukkit.getLogger().severe("Connexion à la base de données échoué.");
			Bukkit.getLogger().severe("Arrêt du plugin.");
			ArenaPlugin.getInstance().getPluginLoader().disablePlugin(ArenaPlugin.getInstance());
		}
	}
	
	public void execute(String sql, Object[] o)
	{
		try 
		{
			PreparedStatement statement = this.connection.prepareStatement(sql);
			for(int i=0;i<o.length;i++)
				statement.setObject(i+1, o[i]);
			statement.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void insert(String table, String[] colonnes, Object[] values)
	{
		String col = "";
		String nVal = "";
		String val = "";
		
		for(String c : colonnes)
			col += c+", ";
		col = col.substring(0, col.length()-2);
		
		for(Object v : values)
		{
			val += v+", ";
			nVal += "?, ";
		}
		val = val.substring(0, val.length()-2);
		nVal = nVal.substring(0, nVal.length()-2);
		
		String r = "INSERT INTO "+table+"("+col+") VALUES("+nVal+")";
		PreparedStatement statement;
		
		try 
		{
			statement = this.connection.prepareStatement(r);
			for(int i=0;i<values.length;i++)
			{
				if(NumberUtils.isNumber(values[i]+""))
					statement.setInt(i+1, (int) values[i]);
				else statement.setString(i+1, (String) values[i]);
			}
			
			statement.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ResultSet read(String sql, Object[] values)
	{
		try
		{
			Bukkit.getLogger().warning(sql);
			PreparedStatement statement = this.connection.prepareStatement(sql);
			for(int i=0;i<values.length;i++)
			{
				if(NumberUtils.isNumber(values[i]+""))
					statement.setInt(i+1, (int) values[i]);
				else statement.setString(i+1, (String) values[i]);
			}
			return statement.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void init()
	{
		Bukkit.getServer().getLogger().info("Vérification des tables...");
		this.execute("CREATE TABLE IF NOT EXISTS `duel_stats` (`id` int(11) NOT NULL AUTO_INCREMENT,`arena` varchar(50) NOT NULL,`type` varchar(10) NOT NULL,`clan1` text NOT NULL,`clan2` text NOT NULL,`winner` int(2) NOT NULL,PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;", new Object[0]);
	}
	
	public void saveDuel(Duel d, Clan w)
	{
		String c1 = "", c2 = "";
		for(Player p : DuelUtils.getPlayerInClan(d, ClanUtils.getById(1)))
			c1 += p.getName()+",";
		for(Player p : DuelUtils.getPlayerInClan(d, ClanUtils.getById(2)))
			c2 += p.getName()+",";
		this.insert("duel_stats", new String[] {"arena","type","clan1","clan2","winner"}, 
									new Object[] {d.getArena().getName(), d.getArena().getType().toString(), c1, c2, w.getId()});
	}
	
	public void registerPlayer(APlayer ap)
	{
		this.insert("duel_player", new String[] {"player", "xp"}, new Object[] {ap.getPlayer().getName(), 0});
	}
	
	public void savePlayer(APlayer ap)
	{
		this.execute("UPDATE duel_player SET xp=? WHERE player=?", new Object[] {ap.getXp(), ap.getPlayer().getName()});
	}
	
	
	public int getClanPoint(Clan c)
	{
		int p = 0;
		ResultSet r = this.read("SELECT * FROM duel_stats WHERE winner = ?", new Object[] {c.getId()});
		try 
		{
			while(r.next())
				p++;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return p;
	}
}
