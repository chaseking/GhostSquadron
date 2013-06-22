package com.chasechocolate.ghostsquadron.game;

import org.bukkit.ChatColor;

public enum TeamColor {
	RED(ChatColor.RED),
	BLUE(ChatColor.BLUE);
	
	private ChatColor chatColor;
	
	private TeamColor(ChatColor chatColor){
		this.chatColor = chatColor;
	}
	
	public ChatColor toChatColor(){
		return chatColor;
	}
}