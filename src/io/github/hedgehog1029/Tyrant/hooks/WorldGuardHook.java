package io.github.hedgehog1029.Tyrant.hooks;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.github.hedgehog1029.frame.hook.IPluginHook;
import org.bukkit.Bukkit;

public class WorldGuardHook implements IPluginHook {
	@Override
	public boolean available() {
		return Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
	}

	public WorldGuardPlugin getWorldGuard() {
		return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	}
}
