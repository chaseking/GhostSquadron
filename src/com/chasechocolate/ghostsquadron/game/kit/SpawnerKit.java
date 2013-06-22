package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class SpawnerKit extends Kit {
	private ItemStack slime = new ItemStack(Material.MONSTER_EGG, 6, EntityType.SLIME.getTypeId());
	private ItemStack skeleton = new ItemStack(Material.MONSTER_EGG, 6, EntityType.SKELETON.getTypeId());
	private ItemStack zombie = new ItemStack(Material.MONSTER_EGG, 6, EntityType.ZOMBIE.getTypeId());
	private ItemStack creeper = new ItemStack(Material.MONSTER_EGG, 1, EntityType.CREEPER.getTypeId());
	
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0);
	
	public SpawnerKit(){
		super("Spawner", new ItemStack(Material.MONSTER_EGG, 1, EntityType.CREEPER.getTypeId()));
	}
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, slime);
		inv.setItem(1, skeleton);
		inv.setItem(2, zombie);
		inv.setItem(3, creeper);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Lots and lots of spawn eggs."
		};
	}
}
