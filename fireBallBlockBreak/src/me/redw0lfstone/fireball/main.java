package me.redw0lfstone.fireball;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.dustplanet.util.SilkUtil;

public class main extends JavaPlugin implements Listener {
	

	public void onEnable() {
		loadConfig();
		SilkUtil.hookIntoSilkSpanwers();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&4+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"));
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&aFireball black damager editor has been loaded"));
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender()
				.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Plugin by: RedW0lfStoneYT"));
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&7Version: " + getDescription().getVersion()));
		this.getServer().getConsoleSender().sendMessage("");
		this.getServer().getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&4+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"));

	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		if (!(new File(getDataFolder() + ".config.yml").exists())) {
			saveDefaultConfig();
		}
//		saveConfig();
	}

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		EntityType entType = event.getEntityType();
		Location loc = event.getEntity().getLocation();
		if (entType.equals(EntityType.FIREBALL)) {
			for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
				for (int y = loc.getBlockY() - 1; y <= loc.getBlockY() + 1; y++) {
					for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++) {
						Block b = loc.getWorld().getBlockAt(x, y, z);
//						Location bLoc = b.getLocation();
						Configuration config = this.getConfig();
						ConfigurationSection breakable = config.getConfigurationSection("blocks.breakable");
						for (String blocks : breakable.getKeys(false)) {
							ConfigurationSection blockList = breakable.getConfigurationSection(blocks);
//							Block item = 
							if (b.getTypeId() == blockList.getInt("type")) {

								if (b.getTypeId() == 52) {
									SilkUtil su = SilkUtil.hookIntoSilkSpanwers();
									short type = su.getSpawnerEntityID(b);
									String name = ChatColor.translateAlternateColorCodes('&', "&e%name% &fSpawner");
									name = name.replace("%name%", su.getCreatureName(type));

									b.getWorld().dropItemNaturally(b.getLocation(),
											su.newSpawnerItem(type, name, 1, false));
								}

								b.breakNaturally();
//								b.setType(Material.STONE);
							} else if (config.getBoolean("vanillaBlocks") == false){
								
								event.setCancelled(true);
							} else {
								
							}

//							event.setCancelled(true);

						}
						
					}
				}
			}
		}
	}

}
