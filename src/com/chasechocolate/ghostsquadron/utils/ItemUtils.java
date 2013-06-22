package com.chasechocolate.ghostsquadron.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	public static ItemStack setItemName(ItemStack item, String name){
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level){
		ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(enchantment, level, true);
		item.setItemMeta(meta);
		
		return item;
	}
}