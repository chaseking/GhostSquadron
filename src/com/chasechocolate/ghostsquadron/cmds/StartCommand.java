package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class StartCommand extends GhostSquadronCommand {
	public StartCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		Player player = (Player) sender;
		
		//Usage: /ghostsquadron start <arena-name>
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("start")){
				if(ArenaUtils.isArena(args[1])){
					Arena arena = ArenaUtils.getArena(args[1]);
					PlayerUtils.sendMessage(player, ChatColor.GREEN + "Attempting to start the arena '" + arena.getName() + "'...");
					
					if(arena.getStatus() == GameStatus.INGAME){
						PlayerUtils.sendMessage(player, ChatColor.RED + "The arena '" + arena.getName() + "' has already started!");
						return;
					} else {
						if(arena.getQueue().getAllInQueue().size() >= Arena.MIN){
							arena.start();
							PlayerUtils.sendMessage(sender, ChatColor.GREEN + "Started the arena '" + arena.getName() + "!");
							return;
						} else {
							PlayerUtils.sendMessage(sender, ChatColor.RED + "Failed to start the arena '" + arena.getName() + "' because there isn't enough players!");
							return;
						}
					}
				} else {
					PlayerUtils.sendMessage(player, ChatColor.RED + "Could not find the arena '" + args[1] + "'! It either doesn't exist or is not running!");
					return;
				}
			}
			
			helper.wrongArguments();
			return;
		}
		
		helper.unknownCommand();
		return;
	}
}