package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.chasechocolate.ghostsquadron.utils.ItemUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class PyroKit extends Kit {
	private ItemStack fireStick = new ItemStack(Material.BLAZE_ROD);
	private ItemStack fireBow = new ItemStack(Material.BOW);
	private ItemStack arrows = new ItemStack(Material.ARROW, 10);
	
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0);
	private PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
	
	public PyroKit(){
		super("Pyro", new ItemStack(Material.BLAZE_ROD));

		fireStick = ItemUtils.setItemName(fireStick, ChatColor.GOLD + "" + ChatColor.BOLD + "Fire Stick");
		fireStick = ItemUtils.addEnchantment(fireStick, Enchantment.FIRE_ASPECT, 1);
		fireBow = ItemUtils.setItemName(fireBow, ChatColor.GOLD + "" + ChatColor.BOLD + "Fire Bow");
		fireBow = ItemUtils.addEnchantment(fireBow, Enchantment.ARROW_FIRE, 1);
	}	
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, fireStick);
		inv.setItem(1, fireBow);
		inv.setItem(9, arrows);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
		player.addPotionEffect(resistance);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Blaze rod with fire aspect",
				"and a flame bow."
		};
	}
}