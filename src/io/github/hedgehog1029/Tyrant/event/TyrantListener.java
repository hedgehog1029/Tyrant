package io.github.hedgehog1029.Tyrant.event;

import java.util.ArrayList;

import io.github.hedgehog1029.Tyrant.ChatProxy;
import io.github.hedgehog1029.Tyrant.Constant;
import io.github.hedgehog1029.Tyrant.handler.TyrantUpdateDescribeHandler;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TyrantListener implements Listener {
	@EventHandler
	public void message(PlayerMessageEvent event) {
		//Handler for Social Spy
		for (Player p : event.getSpies()) {
			ChatProxy.chatToPlayer(p, "§6[§c" + event.getSender().getName() + " §6-> §c" + event.getTarget().getName() + "§6] " + event.getMessage());
		}
	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {
		if (Constant.getDescribers().contains(event.getPlayer())) {
			TyrantUpdateDescribeHandler h = null;
			
			for (TyrantUpdateDescribeHandler t : Constant.getHandlerList()) {
				if (t.getPlayer().getName() == event.getPlayer().getName()) {
					h = t;
					break;
				}
			}
			
			if (h != null) {
				Bukkit.getServer().getPluginManager().callEvent(new PlayerChatDescribeEvent(event.getPlayer(), event.getMessage(), Constant.getDescribers(), h));
				event.setCancelled(true);
			} else {
				event.getPlayer().sendMessage("§6[Tyrant] Fatal error! What did you do?!");
			}
		}
	}
	
	@EventHandler
	public void describe(PlayerChatDescribeEvent event) {
		if (event.getText().equalsIgnoreCase("!q")) {
			ArrayList<Player> temp = Constant.getDescribers();
			temp.remove(event.getSender());
			
			ChatProxy.chatToPlayer(event.getSender(), "§6Exited Tyrant update menu.");
			ChatProxy.chatToPlayer(event.getSender(), "§6==========================");
			
			Constant.updateDescribers(temp);
			
			Constant.setCurrentStory(event.getHandler().getStory());
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 0);
			}
			Bukkit.getServer().broadcastMessage("§6Updates!");
			for (String s : event.getHandler().getStory().getCurrentStory()) {
				Bukkit.getServer().broadcastMessage("§6- " + s);
			}
		} else if (event.getText().equalsIgnoreCase("<")) {
			event.getHandler().deleteStoryComponent(event.getHandler().getStory().getLength() - 1);
			
			ChatProxy.chatToPlayer(event.getSender(), "§6Removed last entry.");
		} else if (event.getText().equalsIgnoreCase("?")) {
			ChatProxy.chatToPlayer(event.getSender(), "§6Current UpdateStory: ");
			
			for (String s : event.getHandler().getStory().getCurrentStory()) {
				ChatProxy.chatToPlayer(event.getSender(), "§6- " + s);
			}
		} else {
			event.getHandler().addToStory(event.getHandler().getStory().getLength(), event.getText(), event.getText());
			ChatProxy.chatToPlayer(event.getSender(), "§6- " + event.getText());
		}
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		
		for (Player p : Constant.getHiders()) {
			event.getPlayer().hidePlayer(p);
		}
		
		if (Constant.getCurrentStory() == null) return;
		
		ChatProxy.chatToPlayer(event.getPlayer(), "§6Latest updates: ");
		
		for (String s : Constant.getCurrentStory().getCurrentStory()) {
			ChatProxy.chatToPlayer(event.getPlayer(), "§6- " + s);
		}
	}
}
