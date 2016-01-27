package org.nathanbraun.minemoba;

import org.bukkit.plugin.java.JavaPlugin;

public class MineMoba extends JavaPlugin {
	
	public static MineMoba plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		getLogger().info("WOOOO the plugin loaded correctly");
	}

	@Override
	public void onDisable() {
		
	}
}
