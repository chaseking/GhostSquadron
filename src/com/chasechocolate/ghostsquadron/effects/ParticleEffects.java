package com.chasechocolate.ghostsquadron.effects;

import net.minecraft.server.v1_6_R2.Packet;
import net.minecraft.server.v1_6_R2.Packet63WorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.utils.ReflectionUtilities;

public enum ParticleEffects { // Credits to microgeek for this class ;D
	HUGE_EXPLOSION("hugeexplosion"),
	LARGE_EXPLODE("largeexplode"),
	FIREWORKS_SPARK("fireworksSpark"),
	BUBBLE("bubble"),
	SUSPEND("suspend"),
	DEPTH_SUSPEND("depthSuspend"),
	TOWN_AURA("townaura"),
	CRIT("crit"),
	MAGIC_CRIT("magicCrit"),
	MOB_SPELL("mobSpell"),
	MOB_SPELL_AMBIENT("mobSpellAmbient"),
	SPELL("spell"),
	INSTANT_SPELL("instantSpell"),
	WITCH_MAGIC("witchMagic"),
	NOTE("note"),
	PORTAL("portal"),
	ENCHANTMENT_TABLE("enchantmenttable"),
	EXPLODE("explode"),
	FLAME("flame"),
	LAVA("lava"),
	FOOTSTEP("footstep"),
	SPLASH("splash"),
	LARGE_SMOKE("largesmoke"),
	CLOUD("cloud"),
	RED_DUST("reddust"),
	SNOWBALL_POOF("snowballpoof"),
	DRIP_WATER("dripWater"),
	DRIP_LAVA("dripLava"),
	SNOW_SHOVEL("snowshovel"),
	SLIME("slime"),
	HEART("heart"),
	ANGRY_VILLAGER("angryVillager"),
	HAPPY_VILLAGER("happyVillager"),
	ICONCRACK("iconcrack_"),
	TILECRACK("tilecrack_");

	private String particleName;

	ParticleEffects(String particleName) {
		this.particleName = particleName;
	}

	public void sendToPlayer(Player player, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
		Packet63WorldParticles packet = new Packet63WorldParticles();
		ReflectionUtilities.setValue(packet, "a", particleName);
		ReflectionUtilities.setValue(packet, "b", (float) loc.getX());
		ReflectionUtilities.setValue(packet, "c", (float) loc.getY());
		ReflectionUtilities.setValue(packet, "d", (float) loc.getZ());
		ReflectionUtilities.setValue(packet, "e", offsetX);
		ReflectionUtilities.setValue(packet, "f", offsetY);
		ReflectionUtilities.setValue(packet, "g", offsetZ);
		ReflectionUtilities.setValue(packet, "h", speed);
		ReflectionUtilities.setValue(packet, "i", count);
		
		sendPacket(player, packet);
	}
	
	private void sendPacket(Player player, Packet packet){
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}