package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.Tyrant.Tyrant;
import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.HelpTopic;
import io.github.hedgehog1029.frame.annotations.Permission;
import io.github.hedgehog1029.frame.annotations.Sender;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Shutdowns {

    @Command(aliases = {"shutdown", "stop"}, desc = "Shutdown the server.", usage = "/shutdown <seconds>")
    @Permission("tyrant.op.shutdown")
    @HelpTopic("Tyrant")
    public void shutdown(@Sender CommandSender sender, int seconds) {
        Bukkit.getServer().broadcastMessage("§e== §6ATTENTION: Server will be shutting down in " + seconds + " seconds. §e==");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.playEffect(p.getLocation(), Effect.RECORD_PLAY, Material.RECORD_8.getId());
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Tyrant.class), new Runnable() {
            int i = seconds;

            @Override
            public void run() {
                i--;

                if (i == 60) {
                    Shutdowns.broadcastTimeLeft(i);
                } else if (i == 30) {
                    Shutdowns.broadcastTimeLeft(i);
                } else if (i == 15) {
                    Shutdowns.broadcastTimeLeft(i);
                } else if (i == 10) {
                    Shutdowns.broadcastTimeLeft(i);
                } else if (i <= 5 && i != 0) {
                    Bukkit.broadcastMessage("§cServer shutting down in " + i + " seconds!");
                } else if (i == 0) {
                    Bukkit.broadcastMessage("§e== §cSERVER SHUTDOWN IMMINENT §e==");

                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.kickPlayer("Server shutting down. Try rejoining in 30 seconds?");
                    }

                    Bukkit.getServer().shutdown();
                }
            }
        }, 1, 20);
    }

    public static final void broadcastTimeLeft(int timeLeft) {
        Bukkit.getServer().broadcastMessage("§6Server will be shutting down in " + timeLeft + " seconds.");
    }
}
