package com.chasechocolate.ghostsquadron.game.map;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.arena.Arena;

public class Map {
	private String name;
	
	private Arena arena;
	
	private Location redSpawn;
	private Location blueSpawn;
	
	private Location point1;
	private Location point2;
	
	private File configFile;
	private FileConfiguration config;
	
	/*
	 * Map Configuration Example:
	 * ----------------------------
	 * arena: MyArena
	 * region:
	 *   point1:
	 *     world: world
	 *     x: 351
	 *     y: 98
	 *     z: 435
	 *   point2:
	 *     world: world
	 *     x: 304
	 *     y: 67
	 *     z: 413
	 * spawns:
	 *   red:
	 *     world: world
	 *     x: 324
	 *     y: 76
	 *     z: 420
	 *   blue:
	 *     world: world
	 *     x: 348
	 *     y: 76
	 *     z: 387
	 */
	
	public Map(String name){
		this.name = name;
		this.configFile = new File(MapUtils.getBaseMapsFile() + File.separator + "" + name + ".yml");
		
		if(!(configFile.exists())){
			try{
				configFile.createNewFile();
				this.config = YamlConfiguration.loadConfiguration(configFile);
			} catch(IOException e){
				e.printStackTrace();
			}
		} else {
			this.config = YamlConfiguration.loadConfiguration(configFile);
		}
		
		if(config.isConfigurationSection("region")){
			World point1World = Bukkit.getWorld(config.getString("region.point1.world"));
			int point1X = config.getInt("region.point1.x");
			int point1Y = config.getInt("region.point1.y");
			int point1Z = config.getInt("region.point1.z");
			Location point1 = new Location(point1World, point1X, point1Y, point1Z);
			
			World point2World = Bukkit.getWorld(config.getString("region.point1.world"));
			int point2X = config.getInt("region.point1.x");
			int point2Y = config.getInt("region.point1.y");
			int point2Z = config.getInt("region.point1.z");
			Location point2 = new Location(point2World, point2X, point2Y, point2Z);

			this.point1 = point1;
			this.point2 = point2;
		}
		
		if(config.isConfigurationSection("spawns")){
			World redSpawnWorld = Bukkit.getWorld(config.getString("spawns.red.world"));
			int redSpawnX = config.getInt("spawns.red.x");
			int redSpawnY = config.getInt("spawns.red.y");
			int redSpawnZ = config.getInt("spawns.red.z");
			Location redSpawn = new Location(redSpawnWorld, redSpawnX, redSpawnY, redSpawnZ);
			
			World blueSpawnWorld = Bukkit.getWorld(config.getString("spawns.blue.world"));
			int blueSpawnX = config.getInt("spawns.blue.x");
			int blueSpawnY = config.getInt("spawns.blue.y");
			int blueSpawnZ = config.getInt("spawns.blue.z");
			Location blueSpawn = new Location(blueSpawnWorld, blueSpawnX, blueSpawnY, blueSpawnZ);
			
			this.redSpawn = redSpawn;
			this.blueSpawn = blueSpawn;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public Location getTeamSpawn(TeamColor team){
		if(team == TeamColor.RED){
			return getRedSpawn();
		} else if(team == TeamColor.BLUE){
			return getBlueSpawn();
		} else {
			return null;
		}
	}
	
	public void setTeamSpawn(TeamColor team, Location loc){
		String teamName = team.toString().toLowerCase();
		
		if(team == TeamColor.RED){
			this.redSpawn = loc;
		} else if(team == TeamColor.BLUE){
			this.blueSpawn = loc;
		}
		
		this.config.set("spawns." + teamName + ".world", loc.getWorld().getName());
		this.config.set("spawns." + teamName + ".x", loc.getBlockX());
		this.config.set("spawns." + teamName + ".y", loc.getBlockY());
		this.config.set("spawns." + teamName + ".z", loc.getBlockZ());
		
		saveConfig();
	}
	
	public Arena getArena(){
		return this.arena;
	}
	
	public void setArena(Arena arena){
		this.arena = arena;
		
		this.config.set("arena", arena.getName());
		
		saveConfig();
	}
	
	public Location getPoint1(){
		return this.point1;
	}
	
	public void setPoint1(Location point1){
		this.point1 = point1;
		
		if(this.configFile.exists()){
			this.config.set("region.point1.world", point1.getWorld().getName());
			this.config.set("region.point1.x", point1.getBlockX());
			this.config.set("region.point1.y", point1.getBlockY());
			this.config.set("region.point1.z", point1.getBlockZ());
			
			saveConfig();
		}
	}
	
	public Location getPoint2(){
		return this.point2;
	}
	
	public void setPoint2(Location point2){
		this.point2 = point2;
		
		if(this.configFile.exists()){
			this.config.set("region.point2.world", point2.getWorld().getName());
			this.config.set("region.point2.x", point2.getBlockX());
			this.config.set("region.point2.y", point2.getBlockY());
			this.config.set("region.point2.z", point2.getBlockZ());
			
			saveConfig();
		}
	}
	
	public Location getRedSpawn(){
		return this.redSpawn;
	}
	
	public void setRedSpawn(Location redSpawn){
		this.redSpawn = redSpawn;
		
		if(this.configFile.exists()){
			this.config.set("spawns.red.world", redSpawn.getWorld().getName());
			this.config.set("spawns.red.x", redSpawn.getBlockX());
			this.config.set("spawns.red.y", redSpawn.getBlockY());
			this.config.set("spawns.red.z", redSpawn.getBlockZ());
			
			saveConfig();
		}
	}
	
	public Location getBlueSpawn(){
		return this.blueSpawn;
	}
	
	public void setBlueSpawn(Location blueSpawn){
		this.blueSpawn = blueSpawn;
		
		if(this.configFile.exists()){
			this.config.set("spawns.blue.world", blueSpawn.getWorld().getName());
			this.config.set("spawns.blue.x", blueSpawn.getBlockX());
			this.config.set("spawns.blue.y", blueSpawn.getBlockY());
			this.config.set("spawns.blue.z", blueSpawn.getBlockZ());
			
			saveConfig();
		}
	}
	
	public File getConfigFile(){
		return this.configFile;
	}
	
	public void setConfigFile(File configFile){
		this.configFile = configFile;
	}
	
	public FileConfiguration getConfig(){
		return this.config;
	}
	
	public void setConfig(FileConfiguration config){
		this.config = config;
	}
	
	public void saveConfig(){
		try{
			config.save(configFile);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}