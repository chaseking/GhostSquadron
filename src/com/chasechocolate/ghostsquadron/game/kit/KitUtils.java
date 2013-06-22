package com.chasechocolate.ghostsquadron.game.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.utils.IconMenu;
import com.chasechocolate.ghostsquadron.utils.IconMenu.OptionClickEvent;
import com.chasechocolate.ghostsquadron.utils.IconMenu.OptionClickEventHandler;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;
import com.chasechocolate.ghostsquadron.utils.Utilities;

public class KitUtils {
	private static HashMap<String, Kit> playerKits = new HashMap<String, Kit>();
	
	private static List<Kit> allKits = new ArrayList<Kit>();
	
	public static void registerKit(Kit kit){
		if(!(allKits.contains(kit))){
			allKits.add(kit);
		}
	}
	
	public static void unregisterKit(Kit kit){
		if(allKits.contains(kit)){
			allKits.remove(kit);
		}
	}
	
	public static List<Kit> getAllKits(){
		return allKits;
	}
	
	public static boolean hasKit(Player player){
		return playerKits.containsKey(player.getName());
	}
	
	public static boolean hasKit(Player player, Kit kit){
		if(kit.getClass().equals(BruiserKit.class)){
			return true;
		} else {
			return player.hasPermission("ghostsquadron.kit." + kit.getName()) || player.isOp();	
		}
	}
	
	public static Kit getKit(Player player){
		Kit kit = null;
		
		if(hasKit(player)){
			kit = playerKits.get(player.getName());
		}
		
		return kit;
	}
	
	public static Kit getKit(String kitName){
		for(Kit kit : allKits){
			if(kit.getName().equalsIgnoreCase(kitName)){
				return kit;
			}
		}
		
		return null;
	}
	
	public static void setKit(Player player, Kit kit){
		playerKits.put(player.getName(), kit);
	}
	
	public static boolean isKit(String kitName){
		for(Kit kit : allKits){
			if(kit.getName().equalsIgnoreCase(kitName)){
				return true;
			}
		}
		
		return false;
	}
	
	public static void openKitSelectionMenu(final Player player){
		final String title = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Choose a Kit";
		final String lineBreak = StringUtils.repeat(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + " ", 45); //Solid line
		final int size = Utilities.round(allKits.size(), 9);
		int index = 0;
		
		IconMenu menu = new IconMenu(title, size, new OptionClickEventHandler(){
			@Override
			public void onOptionClick(OptionClickEvent event){
				String name = ChatColor.stripColor(event.getName());
				
				event.setWillDestroy(true);
				event.setWillClose(true);
				
				if(isKit(name)){
					Kit kit = getKit(name);
					
					if(hasKit(player, kit)){
						setKit(player, kit);
						PlayerUtils.sendMessage(player, ChatColor.GREEN + "You are now " + (Utilities.startsWithVowel(name) ? "an" : "a") + " " + ChatColor.AQUA + name + ChatColor.GREEN + "!"); //Must use proper grammar :)
					} else {
						PlayerUtils.sendMessage(player, ChatColor.RED + "You don't have permission for the " + name + " kit!");
					}
				} else {
					PlayerUtils.sendMessage(player, ChatColor.RED + "Failed to find the kit '" + name + "'!");
				}
			}
		}, GhostSquadron.getInstance());
		
		for(Kit kit : allKits){
			final String kitName = ChatColor.stripColor(kit.getName());
			final ItemStack icon = kit.getIcon();
			String[] kitDesc = kit.getDescription();
			List<String> itemDesc = new ArrayList<String>();
			final String kitMsg = hasKit(player, kit) ? ChatColor.GREEN + "You have this kit!" : ChatColor.RED + "You don't have this kit!";
			
			for(String desc : kitDesc){
				itemDesc.add(desc);
			}
			
			itemDesc.add(lineBreak);
			itemDesc.add(kitMsg);
			
			menu.setOption(index, icon, ChatColor.GOLD + "" + ChatColor.BOLD + kitName, itemDesc.toArray(new String[0]));
			index += 1;
		}
		
		menu.open(player);
	}
}