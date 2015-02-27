package io.github.hedgehog1029.Tyrant;

import io.github.hedgehog1029.Tyrant.handler.TyrantUpdateDescribeHandler;
import io.github.hedgehog1029.Tyrant.handler.sub.Story;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Constant {
	private static ArrayList<Player> hiders = new ArrayList<Player>();
	private static ArrayList<Player> describers = new ArrayList<Player>();
	private static ArrayList<TyrantUpdateDescribeHandler> h = new ArrayList<TyrantUpdateDescribeHandler>();
	private static Story updateStory;
	
	public static ArrayList<Player> getDescribers() {
		return describers;
	}
	
	public static void updateDescribers(ArrayList<Player> desc) {
		describers = desc;
	}
	
	public static ArrayList<TyrantUpdateDescribeHandler> getHandlerList() {
		return h;
	}
	
	public static TyrantUpdateDescribeHandler getHandler(int index) {
		return h.get(index);
	}
	
	/*
	 * Add a handler.
	 * 
	 * @return An int referencing the index of the handler.
	 */
	public static int addHandler(TyrantUpdateDescribeHandler handler) {
		h.add(handler);
		return h.size() - 1;
	}
	
	public static void updateHandlerList(ArrayList<TyrantUpdateDescribeHandler> list) {
		h = list;
	}
	
	public static Story getCurrentStory() {
		return updateStory;
	}
	
	public static void setCurrentStory(Story s) {
		updateStory = s;
	}
	
	public static ArrayList<Player> getHiders() {
		return hiders;
	}
	
	public static void updateHiders(ArrayList<Player> h) {
		hiders = h;
	}
}
