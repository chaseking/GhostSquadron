package com.chasechocolate.ghostsquadron.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {	
	public static Location getLobbyLoc(){
		if(Config.getLocationsFile().exists()){
			World world = Bukkit.getWorld(Config.getLocationsConfig().getString("lobby.world"));
			int x = Config.getLocationsConfig().getInt("lobby.x");
			int y = Config.getLocationsConfig().getInt("lobby.y");
			int z = Config.getLocationsConfig().getInt("lobby.z");
			float yaw = Float.parseFloat(Config.getLocationsConfig().getString("lobby.yaw"));
			float pitch = Float.parseFloat(Config.getLocationsConfig().getString("lobby.pitch"));
			
			Location lobby = new Location(world, x, y, z, yaw, pitch);
			
			return lobby;
		} else {
			Location lobby = Bukkit.getWorlds().get(0).getSpawnLocation();
			return lobby;
		}
	}
	
	public static void setLobbyLoc(Location loc){
		if(Config.getLocationsFile().exists()){
			String world = loc.getWorld().getName();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();

			Config.getLocationsConfig().set("lobby.world", world);
			Config.getLocationsConfig().set("lobby.x", x);
			Config.getLocationsConfig().set("lobby.y", y);
			Config.getLocationsConfig().set("lobby.z", z);
			Config.getLocationsConfig().set("lobby.yaw", yaw);
			Config.getLocationsConfig().set("lobby.pitch", pitch);
			
			Config.saveLocationsFile();
		}
	}
}