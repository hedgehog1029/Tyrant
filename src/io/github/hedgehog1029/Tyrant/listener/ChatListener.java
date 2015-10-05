package io.github.hedgehog1029.Tyrant.listener;

import io.github.hedgehog1029.Tyrant.command.Moderator;
import io.github.hedgehog1029.Tyrant.command.UpdateStory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (UpdateStory.isUpdating(e.getPlayer())) {
            e.setCancelled(true);

            UpdateStory.update(e.getPlayer(), e.getMessage());
        }

        if (Moderator.isPlayerMuted(e.getPlayer())) {
            e.setCancelled(true);

            e.getPlayer().sendMessage("§6[Tyrant] §cYou are muted!");
        }
    }
}
