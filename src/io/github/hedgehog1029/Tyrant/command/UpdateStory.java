package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.frame.loader.Command;
import io.github.hedgehog1029.frame.loader.Sender;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class UpdateStory {

    private static HashSet<UUID> updaters = new HashSet<>();
    private static ArrayList<String> lines = new ArrayList<>();

    @Command(aliases = "update", desc = "Change the current UpdateStory.", helpTopic = "Tyrant", usage = "/update", permission = "tyrant.admin.updatestory")
    public void updateStory(@Sender CommandSender sender) {
        if (!(sender instanceof Player))
            return;

        Player psender = (Player) sender;

        sender.sendMessage("§6[Tyrant] Entering UpdateStory mode. Type !q to quit, and !u to undo.");

        lines.clear();
        updaters.add(psender.getUniqueId());
    }

    public static boolean isUpdating(Player p) {
        return updaters.contains(p.getUniqueId());
    }

    public static void update(Player sender, String chat) {
        // Check if player wants to finish or remove a line.
        if (chat.equalsIgnoreCase("!q")) {
            sender.sendMessage("§6[Tyrant] Exiting UpdateStory mode and broadcasting update.");

            updaters.remove(sender.getUniqueId());

            if (!lines.isEmpty())
                UpdateStory.broadcastStory();
        } else if (chat.equalsIgnoreCase("!u")) {
            sender.sendMessage("Removed " + lines.get(lines.size() - 1));

            lines.remove(lines.size() - 1);
        } else {
            lines.add(chat);
            sender.sendMessage("§e- " + chat);
        }
    }

    public static void broadcastStory() {
        Bukkit.getServer().getOnlinePlayers().forEach(UpdateStory::sendStory);
    }

    public static void sendStory(Player p) {
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.5F);
        p.sendMessage("§eUpdates: ");
        for (String line : lines) {
            p.sendMessage("§e- " + line);
        }
    }

    public static boolean isUpdateAvailable() {
        return !lines.isEmpty();
    }
}
