package io.github.hedgehog1029.Tyrant.hooks;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.hedgehog1029.frame.hook.IPluginHook;
import org.bukkit.Bukkit;

public class ProtocolLibHook implements IPluginHook {
	@Override
	public boolean available() {
		return Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
	}

	public ProtocolManager getProtocolManager() {
		return ProtocolLibrary.getProtocolManager();
	}
}
