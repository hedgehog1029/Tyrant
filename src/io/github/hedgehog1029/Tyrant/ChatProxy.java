package io.github.hedgehog1029.Tyrant;

import java.util.ArrayList;

import io.github.hedgehog1029.Tyrant.event.PlayerMessageEvent;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatProxy {
	public static void chatToPlayer(Player player, String msg) {
		player.sendMessage(msg);
	}
	
	public static void messagePlayer(Player target, CommandSender sender, String[] msg, ArrayList<Player> spies) {
		target.sendMessage("§6[§c" + sender.getName() + " §6-> §cyou§6] " + msg);
		sender.sendMessage("§6[§cyou §6-> §c" + target.getName() + "§6] " + msg);
		Bukkit.getServer().getPluginManager().callEvent(new PlayerMessageEvent(sender, target, msg, spies));
	}
}
