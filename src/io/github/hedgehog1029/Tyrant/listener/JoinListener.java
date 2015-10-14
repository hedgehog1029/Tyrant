package io.github.hedgehog1029.Tyrant.listener;

import io.github.hedgehog1029.Tyrant.command.Hide;
import io.github.hedgehog1029.Tyrant.command.Moderator;
import io.github.hedgehog1029.Tyrant.command.UpdateStory;
import io.github.hedgehog1029.Tyrant.mod.FancyName;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if (Moderator.isPlayerBanned(e.getPlayer())) {
            e.setJoinMessage("");
            e.getPlayer().kickPlayer("You are banned!");
            return;
        }

        Hide.update();

        // Generate a random coloured name using FancyName
        e.getPlayer().setDisplayName(FancyName.generateColor() + e.getPlayer().getName() + ChatColor.RESET);

        // Show UpdateStory
        if (UpdateStory.isUpdateAvailable())
            UpdateStory.sendStory(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (Moderator.isPlayerBanned(e.getPlayer())) {
            e.setQuitMessage("");
        }

        Hide.removePlayer(e.getPlayer());
    }
}
