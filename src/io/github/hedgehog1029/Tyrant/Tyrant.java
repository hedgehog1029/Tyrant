package io.github.hedgehog1029.Tyrant;

import io.github.hedgehog1029.Tyrant.event.TyrantListener;
import io.github.hedgehog1029.Tyrant.handler.TyrantUpdateDescribeHandler;
import io.github.hedgehog1029.Tyrant.handler.sub.Story;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Tyrant extends JavaPlugin {
	
	ArrayList<Player> spies = new ArrayList<Player>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TyrantListener(), this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("msg") || cmd.getName().equalsIgnoreCase("tell")) {
			if (sender.hasPermission("tyrant.player.msg")) {
				if (args.length == 2) {
					Player target = getServer().getPlayer(args[0]);
					
					ChatProxy.messagePlayer(target, sender, Arrays.copyOfRange(args, 1, args.length), spies);
					
					return true;
				} else return false;
			} else return false;
		} else if (cmd.getName().equalsIgnoreCase("spy")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("tyrant.admin.socialspy") || sender.isOp()) {
					if (spies.contains((Player) sender)) {
						spies.remove((Player) sender);
						ChatProxy.chatToPlayer((Player) sender, "§6[Tyrant] Social Spy turned off.");
					} else {
						spies.add((Player) sender);
						ChatProxy.chatToPlayer((Player) sender, "§6[Tyrant] Social Spy turned on.");
					}
				} else return false;
			} else return false;
		} else if (cmd.getName().equalsIgnoreCase("inv")) {
			if (sender instanceof Player && sender.hasPermission("tyrant.admin.inv")) {
				if (args.length == 1) {
					Player invUser = getServer().getPlayer(args[0]);
					Inventory inv = invUser.getInventory();
					
					Player su = (Player) sender;
					
					su.closeInventory();
					su.openInventory(inv);
					ChatProxy.chatToPlayer(su, "§6[Tyrant] Opened §6" + invUser.getName() + "§6's inventory.");
				} else return false;
			} else return false;
		} else if (cmd.getName().equalsIgnoreCase("hide")) {
			if (sender instanceof Player && sender.hasPermission("tyrant.admin.hide")) {
				Player hider = (Player) sender;
				ArrayList<Player> hiders = Constant.getHiders();
				
				if (hiders.contains(hider)) {
					for (Player p : getServer().getOnlinePlayers()) {
						p.showPlayer(hider);
						hiders.remove(hider);
					}
					ChatProxy.chatToPlayer(hider, "§6[Tyrant] You are no longer hidden.");
					Constant.updateHiders(hiders);
				} else {
					for (Player p : getServer().getOnlinePlayers()) {
						p.hidePlayer(hider);
						hiders.add(hider);
					}
					ChatProxy.chatToPlayer(hider, "§6[Tyrant] You are now hidden.");
					Constant.updateHiders(hiders);
				}
			} else return false;
		} else if (cmd.getName().equalsIgnoreCase("update")) {
			if (sender.hasPermission("tyrant.admin.update.announce") && sender instanceof Player) {
				ArrayList<Player> def = Constant.getDescribers();
				def.add((Player) sender);
				Constant.updateDescribers(def);
				
				Constant.addHandler(new TyrantUpdateDescribeHandler(new Story(1), (Player) sender));
				sender.sendMessage("§6[Tyrant] Entering UpdateStory mode...");
				sender.sendMessage("§6=====================================");
				sender.sendMessage("§6Current UpdateStory: ");
			} else return false;
		} else if (cmd.getName().equalsIgnoreCase("stop")) {
			if (sender.hasPermission("tyrant.admin.DANGER.stop")) {
				
			}
		}
		return true;
	}
}
