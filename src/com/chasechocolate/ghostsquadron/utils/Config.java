package com.chasechocolate.ghostsquadron.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chasechocolate.ghostsquadron.GhostSquadron;

public class Config {
	private static GhostSquadron plugin;
	
	private static File dataFolder;
	
	private static File configFile;
	private static File locationsFile;
	
	private static File baseMapsFile;
	
	private static FileConfiguration config;
	private static FileConfiguration locationsConfig;
	
	@SuppressWarnings("static-access")
	public Config(GhostSquadron plugin){
		this.plugin = plugin;
		
		this.dataFolder = plugin.getDataFolder();
		
		this.configFile = new File(plugin.getDataFolder(), "config.yml");
		this.locationsFile = new File(plugin.getDataFolder(), "locations.yml");
		
		this.baseMapsFile = new File(plugin.getDataFolder() + File.separator + "maps");
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
		this.locationsConfig = YamlConfiguration.loadConfiguration(this.locationsFile);
	}
	
	//CREATING FILES
	public static void createAllFiles(){
		if(!(configFile.exists())){
			plugin.log("Found no config.yml! Creating one for you...");
			plugin.saveResource("config.yml", true);
			plugin.log("Successfully created config.yml!");
		}
		
		if(!(locationsFile.exists())){
			plugin.log("Found no locations.yml! Creating one for you...");
			plugin.saveResource("locations.yml", true);
			plugin.log("Successfully created locations.yml!");
		}
		
		if(!(baseMapsFile.exists())){
			plugin.log("Found no maps folder! Creating one for you...");
			
			baseMapsFile.mkdirs();
			plugin.log("Successfully created arenas.yml!");
		}
	}
	
	public static FileConfiguration createMapFile(String mapName){
		File mapFile = new File(dataFolder + File.separator + "maps", mapName + ".yml");
		FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapFile);
		
		if(!(mapFile.exists())){
			try{
				mapFile.createNewFile();
				mapConfig.load(mapFile);
			} catch(IOException e){
				e.printStackTrace();
			} catch(InvalidConfigurationException e){
				e.printStackTrace();
			}
		}
		
		return mapConfig;
	}
	
	//GETTING FILES
	public static File getConfigFile(){
		return configFile;
	}
	
	public static File getLocationsFile(){
		return locationsFile;
	}
	
	public static File getArenasFile(){
		return configFile;
	}
	
	public static File getMapsFile(String mapName){
		File mapFile = new File(dataFolder + File.separator + "maps", mapName + ".yml");
		
		if(mapFile.exists()){
			return mapFile;
		} else {
			return null;
		}
	}
	
	public static File getBaseMapsFile(){
		return baseMapsFile;
	}
	
	//ACCESSING FILECONFIGURATIONS
	public static FileConfiguration getConfig(){
		return config;
	}
	
	public static FileConfiguration getLocationsConfig(){
		return locationsConfig;
	}
	
	public static FileConfiguration getFileConfig(File file){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	//SAVING FILES
	public static void saveConfigFile(){
		saveFile(configFile, config);
	}
	
	public static void saveLocationsFile(){
		saveFile(locationsFile, locationsConfig);
	}
	
	public static void saveFile(File file, FileConfiguration config){
		try{
			config.save(file);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void saveAllFiles(){
		saveConfigFile();
		saveLocationsFile();
	}
}