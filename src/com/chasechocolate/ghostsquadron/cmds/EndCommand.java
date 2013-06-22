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

public class EndCommand extends GhostSquadronCommand {
	public EndCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		Player player = (Player) sender;
		
		//Usage: /ghostsquadron end <arena-name>
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("end")){
				if(ArenaUtils.isArena(args[1])){
					Arena arena = ArenaUtils.getArena(args[1]);
					
					if(arena.getStatus() == GameStatus.INGAME){
						arena.end(true);
						PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully force-ended the arena '" + arena.getName() + "'! It is now restarting...");
					} else {
						PlayerUtils.sendMessage(player, ChatColor.RED + "The arena '" + arena.getName() + "' is currently not in-game!");
					}
					
					return;
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