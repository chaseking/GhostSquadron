package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.game.map.Map;
import com.chasechocolate.ghostsquadron.game.map.MapUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class ArenaCommand extends GhostSquadronCommand {
	public ArenaCommand(GhostSquadron plugin){
		super(plugin);
	}

	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			//Usage: /ghostsquadron arena delete <name>
			if(args.length == 3){
				if(args[0].equalsIgnoreCase("arena")){
					if(args[1].equalsIgnoreCase("delete")){
						if(ArenaUtils.isArena(args[2])){
							Arena arena = ArenaUtils.getArena(args[2]);
							arena.end(true);
							ArenaUtils.deleteArena(arena);
							
							PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully deleted the arena with the name '" + args[2] + "'!");
							return;
						} else {
							PlayerUtils.sendMessage(player, ChatColor.RED + "Could not find the arena '" + args[2] + "'!");
							return;
						}
					}
				}
			}
			
			//Usage: /ghostsquadron arena create <name> map <map-name>
			if(args.length == 5){
				if(args[0].equalsIgnoreCase("arena")){
					if(args[1].equalsIgnoreCase("create")){
						if(!(ArenaUtils.isArena(args[2]))){
							if(args[3].equalsIgnoreCase("map")){
								if(MapUtils.isMap(args[4])){
									if(!(MapUtils.isMapUsed(args[4]))){
										Map map = MapUtils.getMap(args[4]);
										Arena arena = new Arena(args[2], map);
										
										map.setArena(arena);
										ArenaUtils.addArena(arena);
										ArenaUtils.loadArenas();
										
										PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully created an arena with the name '" + arena.getName() + "' linked with the map '" + map.getName() + "'!");
										return;
									} else {
										PlayerUtils.sendMessage(player, ChatColor.RED + "An existing arena is using the map '" + args[4] + "'!");
										return;
									}
								} else {
									PlayerUtils.sendMessage(player, ChatColor.RED + "Could not find a map with the name '" + args[4] + "!");
									return;
								}
							}
						} else {
							PlayerUtils.sendMessage(player, ChatColor.RED + "Found an existing arena with the name '" + args[2] + "'!");
							return;
						}
					}
				}
			}
			
			helper.wrongArguments();			
			return;
		} else {
			helper.noConsole();
			return;
		}
	}
}