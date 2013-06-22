package com.chasechocolate.ghostsquadron.utils;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.mysql.SQLUtils;

public class PlayerStats {
	private String playerName;
	
	private boolean isLoaded;
	
	private int kills;
	private int deaths;
	private int highestKillstreak;
	private int gamesPlayed;
	private int timesOnRed;
	private int timesOnBlue;
	private int timeInGame;
	
	private int currentKillstreak;
	
	private Set<Integer> killstreaks = new HashSet<Integer>();
	
	private static GhostSquadron plugin;
	
	@SuppressWarnings("static-access")
	public PlayerStats(Player player){
		this.playerName = player.getName();
		this.plugin = GhostSquadron.getInstance();
	}
	
	public void load(){
		if(Config.getConfig().getBoolean("mysql.enabled")){
			new BukkitRunnable(){
				@Override
				public void run(){
					isLoaded = false;
					ResultSet rs = SQLUtils.getResultSet("SELECT * FROM player_stats WHERE USERNAME = '" + playerName + "'");
					
					try{
						while(rs.next()){
							isLoaded = true;
							
							kills = Integer.parseInt(rs.getString("total_kills"));
							deaths = Integer.parseInt(rs.getString("total_deaths"));
							highestKillstreak = Integer.parseInt(rs.getString("highest_killstreak"));
							gamesPlayed = Integer.parseInt(rs.getString("games_played"));
							timesOnRed = Integer.parseInt(rs.getString("times_on_red"));
							timesOnBlue = Integer.parseInt(rs.getString("times_on_blue"));
							timeInGame = Integer.parseInt(rs.getString("time_in_game"));
						}
					} catch(Exception e){
						e.printStackTrace();
					}
					
					if(!(isLoaded)){
						SQLUtils.executeUpdate("INSERT INTO player_stats(username, total_kills, total_deaths, highest_killstreak, games_played, times_on_red, times_on_blue, time_in_game) VALUES ('" + playerName + "', 0, 0, 0, 0, 0, 0, 0");
					}
				}
			}.runTaskLaterAsynchronously(plugin, 20L);
		}
	}
	
	public void pushStats(){
		if(Config.getConfig().getBoolean("mysql.enabled")){
			new BukkitRunnable(){
				@Override
				public void run(){
					ResultSet rs = SQLUtils.getResultSet("SELECT * FROM player_stats WHERE USERNAME = '" + playerName + "'");
					
					if(isLoaded){
						try{
							while(rs.next()){
								rs.updateString("total_kills", kills + "");
								rs.updateString("total_deaths", deaths + "");
								rs.updateString("highest_killstreak", highestKillstreak + "");
								rs.updateString("games_played", gamesPlayed + "");
								rs.updateString("times_on_red", timesOnRed + "");
								rs.updateString("times_on_blue", timesOnBlue + "");
								rs.updateString("time_in_game", timeInGame + "");
								
								rs.updateRow();
							}
						} catch(Exception e){
							e.printStackTrace();
						}
					} else {
						return;
					}
				}
			}.runTaskAsynchronously(plugin);
		}
	}
	
	public int getKills(){
		return this.kills;
	}
	
	public void setKills(int kills){
		this.kills = kills;
	}
	
	public int getDeaths(){
		return this.deaths;
	}
	
	public void setDeaths(int deaths){
		this.deaths = deaths;
	}
	
	public int getCurrentKillstreak(){
		return this.currentKillstreak;
	}
	
	public void setCurrentKillstreak(int currentKillstreak){
		this.currentKillstreak = currentKillstreak;
	}
	
	public int getHighestKillstreak(){
		if(!(killstreaks.isEmpty())){
			return Collections.max(killstreaks);
		} else {
			return 0;
		}
	}
	
	public void addKillstreak(int killstreak){
		this.killstreaks.add(killstreak);
	}
	
	public void manageKill(){
		setKills(getKills() + 1);
		setCurrentKillstreak(getCurrentKillstreak() + 1);
	}
	
	public void manageDeath(){
		setDeaths(getDeaths() + 1);
		addKillstreak(getCurrentKillstreak());
		setCurrentKillstreak(0);
	}
}