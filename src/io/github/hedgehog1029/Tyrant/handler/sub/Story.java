package io.github.hedgehog1029.Tyrant.handler.sub;

import java.util.ArrayList;

public class Story {
	private ArrayList<StoryComponent> story = new ArrayList<StoryComponent>();
	
	private int id;
	
	public Story(int id, StoryComponent... comp) {
		this.id = id;
		for (StoryComponent s : comp) {
			story.add(s);
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getLength() {
		return story.size();
	}
	
	public Story getStory() {
		return this;
	}
	
	public StoryComponent get(int index) {
		return story.get(index);
	}
	
	public void add(StoryComponent e) {
		story.add(e);
	}
	
	public void remove(StoryComponent e) {
		story.remove(e);
	}
	
	public void remove(int index) {
		story.remove(index);
	}
	
	public ArrayList<String> getCurrentStory() {
		ArrayList<String> s = new ArrayList<String>();
		
		for (StoryComponent e : story) {
			s.add(e.getCommand());
		}
		return s;
	}
	
	public void clear() {
		story.clear();
	}
}
