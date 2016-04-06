package io.github.hedgehog1029.Tyrant;

import io.github.hedgehog1029.Tyrant.command.*;
import io.github.hedgehog1029.Tyrant.hooks.ProtocolLibHook;
import io.github.hedgehog1029.Tyrant.hooks.WorldEditHook;
import io.github.hedgehog1029.Tyrant.hooks.WorldGuardHook;
import io.github.hedgehog1029.Tyrant.listener.ChatListener;
import io.github.hedgehog1029.Tyrant.listener.JoinListener;
import io.github.hedgehog1029.Tyrant.listener.SleepListener;
import io.github.hedgehog1029.Tyrant.mod.FancyName;
import io.github.hedgehog1029.Tyrant.mod.RegionMessage;
import io.github.hedgehog1029.Tyrant.util.PacketHelper;
import io.github.hedgehog1029.frame.Frame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tyrant extends JavaPlugin {
	public void onEnable() {
		Frame.addModule(Hide.class);
		Frame.addModule(InventoryViewer.class);
		Frame.addModule(Messaging.class);
		Frame.addModule(Shutdowns.class);
		Frame.addModule(UpdateStory.class);
		Frame.addModule(Moderator.class);
		Frame.addModule(BeanShell.class);
		Frame.addModule(Utilities.class);

		Frame.addModule(RegionMessage.class);
		Frame.addModule(PacketHelper.class);

		Frame.addHook(WorldEditHook.class);
		Frame.addHook(WorldGuardHook.class);
		Frame.addHook(ProtocolLibHook.class);

		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new SleepListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setDisplayName(FancyName.generateColor() + p.getName() + ChatColor.RESET);
		}
	}
}
