package com.chasechocolate.ghostsquadron.utils;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Localization {
	public static final String CHAT_TITLE = ChatColor.GRAY + "[" + ChatColor.YELLOW + "Ghost Squadron" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
	
	public static final Permission CMD_JOIN_PERM = new Permission("ghostsquadron.command.play");
	public static final Permission CMD_LEAVE_PERM = new Permission("ghostsquadron.command.play");
	public static final Permission CMD_ARENA_PERM = new Permission("ghostsquadron.command.arena");
	public static final Permission CMD_MAP_PERM = new Permission("ghostsquadron.command.map");
	public static final Permission CMD_SETSPAWN_PERM = new Permission("ghostsquadron.command.setspawn");
	public static final Permission CMD_START_PERM = new Permission("ghostsquadron.command.start");
	public static final Permission CMD_END_PERM = new Permission("ghostsquadron.command.end");
	public static final Permission SIGN_PERM = new Permission("ghostsquadron.sign");
	
	public static final PotionEffect INVISIBILITY = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15);
}