package io.github.hedgehog1029.Tyrant.handler;

import org.bukkit.entity.Player;

import io.github.hedgehog1029.Tyrant.handler.sub.Story;
import io.github.hedgehog1029.Tyrant.handler.sub.StoryComponent;

public class TyrantUpdateDescribeHandler {
	private Story story;
	private Player player;
	
	public TyrantUpdateDescribeHandler(Story story, Player player) {
		this.story = story;
	}
	
	public void addToStory(int key, String command, String response) {
		story.add(new StoryComponent(key, command, response));
	}
	
	public StoryComponent getStory(int index) {
		return story.get(index);
	}
	
	public Story getStory() {
		return story;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void deleteStoryComponent(int index) {
		story.remove(index);
	}
	
	public void deleteStoryComponent(StoryComponent e) {
		story.remove(e);
	}
}
