package io.github.hedgehog1029.Tyrant;

import io.github.hedgehog1029.Tyrant.command.*;
import io.github.hedgehog1029.Tyrant.listener.ChatListener;
import io.github.hedgehog1029.Tyrant.listener.JoinListener;
import io.github.hedgehog1029.Tyrant.listener.SleepListener;
import io.github.hedgehog1029.Tyrant.mod.FancyName;
import io.github.hedgehog1029.frame.Frame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tyrant extends JavaPlugin {
	public void onEnable() {
		Frame.addCommandeer(Hide.class);
		Frame.addCommandeer(InventoryViewer.class);
		Frame.addCommandeer(Messaging.class);
		Frame.addCommandeer(Shutdowns.class);
		Frame.addCommandeer(UpdateStory.class);
		Frame.addCommandeer(Moderator.class);
		Frame.addCommandeer(BeanShell.class);

		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new SleepListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setDisplayName(FancyName.generateColor() + p.getName() + ChatColor.RESET);
		}
	}
}
