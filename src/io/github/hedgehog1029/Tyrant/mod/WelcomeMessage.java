package io.github.hedgehog1029.Tyrant.mod;

import io.github.hedgehog1029.Tyrant.util.Title;
import org.bukkit.entity.Player;

public class WelcomeMessage {
	public static void welcome(Player player) {
		Title title = new Title(String.format("§6Welcome to Mabel, %s!", player.getName()),
				"§aEnjoy your stay.",
				1, 8, 1);

		title.send(player);
	}
}
