package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.HelpTopic;
import io.github.hedgehog1029.frame.annotations.Permission;
import io.github.hedgehog1029.frame.annotations.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Hide {

    private static ArrayList<UUID> hiders = new ArrayList<>();

    @Command(aliases = "hide", desc = "Hide yourself!", usage = "/hide")
    @Permission("tyrant.admin.hide")
    @HelpTopic("Tyrant")
    public void hide(@Sender CommandSender sender) {
        if (!(sender instanceof Player))
            return;

        Player player = (Player) sender;
        if (hiders.contains(player.getUniqueId())) {
            hiders.remove(player.getUniqueId());
            player.sendMessage("§6[Tyrant] You are no longer hidden.");
        } else {
            hiders.add(player.getUniqueId());
            player.sendMessage("§6[Tyrant] You are now hidden.");
        }

        Hide.update();
    }

    public static void update() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            for (Player op : Bukkit.getServer().getOnlinePlayers()) {
                p.showPlayer(op);
                op.showPlayer(p);
            }

            for (UUID u : hiders) {
                Player hider = Bukkit.getPlayer(u);

                if (p.getUniqueId().equals(hider.getUniqueId()))
                    continue;

                p.hidePlayer(hider);
            }
        }
    }

    public static void removePlayer(Player p) {
        hiders.remove(p.getUniqueId());
    }
}
