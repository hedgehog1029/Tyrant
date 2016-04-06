package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.HelpTopic;
import io.github.hedgehog1029.frame.annotations.Permission;
import io.github.hedgehog1029.frame.annotations.Sender;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utilities {
	@Command(aliases = "setspawn", usage = "setspawn", desc = "Set the spawn point of the current world.")
	@Permission("tyrant.admin.setspawn")
	@HelpTopic("Tyrant")
	public void setspawn(@Sender CommandSender commandSender) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage("§6[Tyrant] You must be a player to use this command!");
			return;
		}

		Player player = (Player) commandSender;

		Location l = player.getLocation();
		player.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());

		player.sendMessage(String.format("§6[Tyrant] Set the spawnpoint of %s to %d, %d, %d",
				l.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
	}

	@Command(aliases = "spawn", usage = "spawn", desc = "Go the the current spawn point.")
	@Permission("tyrant.commands.spawn")
	public void spawn(@Sender CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§6[Tyrant] You must be a player to use this command!");
			return;
		}

		Player player = (Player) sender;

		player.teleport(player.getWorld().getSpawnLocation());
		player.sendMessage("§6[Tyrant] Teleported to spawn.");
	}
}
