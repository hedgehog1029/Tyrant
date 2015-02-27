package io.github.hedgehog1029.Tyrant.event;

import io.github.hedgehog1029.Tyrant.handler.TyrantUpdateDescribeHandler;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChatDescribeEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	private Player sender;
	private String chat;
	private ArrayList<Player> describers;
	private TyrantUpdateDescribeHandler handler;
	
	public PlayerChatDescribeEvent(Player sender, String chat, ArrayList<Player> desc, TyrantUpdateDescribeHandler handler) {
		this.sender = sender;
		this.chat = chat;
		this.describers = desc;
		this.handler = handler;
	}
	
	public Player getSender() {
		return this.sender;
	}
	
	public String getText() {
		return this.chat;
	}
	
	public TyrantUpdateDescribeHandler getHandler() {
		return this.handler;
	}
	
	/*
	 * Get the current list of describers.
	 * This is used to cancel a player's chat events.
	 * 
	 * @return An ArrayList<Player> containing the current describers.
	 */
	public ArrayList<Player> getDescribers() {
		return this.describers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
