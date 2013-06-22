package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class LeaveCommand extends GhostSquadronCommand {
	public LeaveCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		//Usage: /ghostsquadron leave
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			if(GameUtils.isInGame(player)){
				ArenaUtils.getPlayerArena(player).removePlayer(player);
				return;
			} else {
				PlayerUtils.sendMessage(player, ChatColor.RED + "You are currently not in a game!");
				return;
			}
		} else {
			helper.noConsole();
			return;
		}
	}
}