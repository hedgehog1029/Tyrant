package io.github.hedgehog1029.Tyrant.event;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMessageEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	private String[] message;
	private CommandSender sender;
	private Player target;
	private ArrayList<Player> spies;
	
	public PlayerMessageEvent(CommandSender sender, Player target, String[] msg, ArrayList<Player> spies) {
		this.message = msg;
		this.sender = sender;
		this.target = target;
		this.spies = spies;
	}
	
	public String[] getMessage() {
		return this.message;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	
	public Player getTarget() {
		return this.target;
	}
	
	public ArrayList<Player> getSpies() {
		return this.spies;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
