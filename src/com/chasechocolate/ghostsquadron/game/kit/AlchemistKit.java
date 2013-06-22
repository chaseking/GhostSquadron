package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.chasechocolate.ghostsquadron.utils.ItemUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class AlchemistKit extends Kit {
	private ItemStack harming;
	private ItemStack poison;
	private ItemStack heal;
	private ItemStack reveal;
	
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
	private PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
	
	public AlchemistKit(){
		super("Alchemist", new ItemStack(Material.POTION));
		
		harming = new Potion(PotionType.INSTANT_DAMAGE, 2).splash().toItemStack(3);
		poison = new Potion(PotionType.POISON, 1).splash().toItemStack(3);
		heal = new Potion(PotionType.INSTANT_HEAL, 2).splash().toItemStack(3);
		reveal = ItemUtils.setItemName(new Potion(PotionType.INVISIBILITY, 2).splash().toItemStack(2), ChatColor.RESET + "Splash Potion of Revealing");
	}
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, harming);
		inv.setItem(1, poison);
		inv.setItem(2, heal);
		inv.setItem(3, reveal);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
		player.addPotionEffect(resistance);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Lots and lots of potions."
		};
	}
}