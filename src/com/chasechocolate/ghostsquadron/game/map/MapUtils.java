package com.chasechocolate.ghostsquadron.game.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.Config;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class MapUtils {
	private static File baseMapsFile;
	
	private static List<Map> allMaps = new ArrayList<Map>();
	
	@SuppressWarnings("static-access")
	public MapUtils(){
		this.baseMapsFile = Config.getBaseMapsFile();;
		
		loadMaps();
	}
	
	public static void loadMaps(){
		allMaps.clear();
		
		for(File mapFile : baseMapsFile.listFiles()){
			if(!(mapFile.getName().startsWith("."))){
				String mapName = mapFile.getName().replaceAll(".yml", "");
				Map map = new Map(mapName);
				
				allMaps.add(map);
			}
		}
	}
	
	public static List<Map> getAllMaps(){
		return allMaps;
	}
	
	public static File getBaseMapsFile(){
		return baseMapsFile;
	}
	
	public static boolean isMap(String mapName){
		for(Map map : allMaps){
			if(map.getName().equalsIgnoreCase(mapName)){
				return true;
			}
		}
		
		return false;
	}
	
	public static Map getMap(String mapName){
		Map map = null;
		
		if(isMap(mapName)){
			for(Map allTheMaps : allMaps){
				if(allTheMaps.getName().equalsIgnoreCase(mapName)){
					map = allTheMaps;
				}
			}
		}
		
		return map;
	}
	
	public static boolean isMapUsed(String mapName){
		boolean used = false;
		
		for(Arena arena : ArenaUtils.getAllArenas()){
			if(arena.getMap().getName().equalsIgnoreCase(mapName)){
				used = true;
			}
		}
		
		return used;
	}
	
	public static void createMap(String mapName, Selection selection){
		if(!(isMap(mapName))){
			Map map = new Map(mapName);
			map.setPoint1(selection.getMaximumPoint());
			map.setPoint2(selection.getMinimumPoint());
			
			saveMapsConfig(mapName);
		}
	}
	
	public static void deleteMap(String mapName){
		if(isMap(mapName)){
			Map map = getMap(mapName);
			map.getConfigFile().delete();
		}
	}
	
	
	public static void saveMapsConfig(String mapName){
		if(isMap(mapName)){
			Map map = getMap(mapName);
			
			try{
				map.getConfig().save(map.getConfigFile());
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}