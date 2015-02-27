package io.github.hedgehog1029.Tyrant.handler.sub;

public class StoryComponent {
	private int key;
	private String command;
	private String response;
	
	public StoryComponent(int key, String command, String response) {
		this.key = key;
		this.command = command;
		this.response = response;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public String getCommand() {
		return this.command;
	}
	
	public String getResponse() {
		return this.response;
	}
}
