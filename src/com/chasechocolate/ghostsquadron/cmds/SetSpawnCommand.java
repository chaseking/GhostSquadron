package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.map.Map;
import com.chasechocolate.ghostsquadron.game.map.MapUtils;
import com.chasechocolate.ghostsquadron.utils.LocationUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class SetSpawnCommand extends GhostSquadronCommand {	
	public SetSpawnCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		Player player = (Player) sender;
		Location loc = player.getLocation();
		
		//Usage: /ghostsquadron setspawn lobby
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("setspawn")){
				if(args[1].equalsIgnoreCase("lobby")){
					LocationUtils.setLobbyLoc(loc);
					PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully set the lobby spawn location!");
					
					return;
				}
			}
		}
		
		//Usage: /ghostsquadron setspawn map <map> <red/blue>
		if(args.length == 4){
			if(args[0].equalsIgnoreCase("setspawn")){
				if(args[1].equalsIgnoreCase("map")){
					if(MapUtils.isMap(args[2])){
						Map map = MapUtils.getMap(args[2]);
						
						if(args[3].equalsIgnoreCase("red")){
							map.setRedSpawn(loc);
							PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully set the " + args[3].toLowerCase() + " team's spawn location in the map '" + args[2] + "'!");
							
							return;
						} else if(args[3].equalsIgnoreCase("blue")){
							map.setBlueSpawn(loc);
							PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully set the " + args[3].toLowerCase() + " team's spawn location in the map '" + args[2] + "'!");
							
							return;
						} else {
							PlayerUtils.sendMessage(player, ChatColor.RED + "Unknown team, use either red or blue.");
							return;
						}
					} else {
						PlayerUtils.sendMessage(player, ChatColor.RED + "Could not find the map '" + args[2] + "'!");
						return;
					}
				}
			}
			
			helper.wrongArguments();
			return;
		}
		
		helper.unknownCommand();
		return;
	}
}