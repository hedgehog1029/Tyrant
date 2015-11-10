package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.Tyrant.Tyrant;
import io.github.hedgehog1029.Tyrant.util.IconMenu;
import io.github.hedgehog1029.frame.loader.Command;
import io.github.hedgehog1029.frame.loader.HelpTopic;
import io.github.hedgehog1029.frame.loader.Permission;
import io.github.hedgehog1029.frame.loader.Sender;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Moderator {

    private static ArrayList<UUID> mutedPlayers = new ArrayList<>();
    private static ArrayList<UUID> bannedPlayers = new ArrayList<>();

    @Command(aliases = "mod", desc = "Moderation tools", usage = "/mod")
    @Permission("tyrant.moderator.mod")
    @HelpTopic("Tyrant")
    public void moderate(@Sender CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[Tyrant] You can only use /mod as a player!");
            return;
        }

        Player psender = (Player) sender;

        psender.sendMessage("§6[Tyrant] Opened mod menu.");
        IconMenu menu = new IconMenu("Moderation Tools", 54, event -> {
            IconMenu playerMenu = this.getPlayerMenu(event.getName());

            Moderator.addModTools(playerMenu, Bukkit.getPlayer(event.getName()));

            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Tyrant.class), () -> {
                playerMenu.open(event.getPlayer(), Bukkit.getPlayer(event.getName()), "");
            }, 3);

            event.setWillClose(true);
            event.setWillDestroy(true);
        }, JavaPlugin.getPlugin(Tyrant.class));

        this.populate(menu);

        menu.open(psender, null, "");
    }

    @Command(aliases = "unban", desc = "Unban a player.", usage = "/unban <player>")
    @Permission("tyrant.admin.bans")
    @HelpTopic("Tyrant")
    public void unban(@Sender CommandSender sender, OfflinePlayer p) {
        Moderator.unban(p.getUniqueId());
    }

    public void populate(IconMenu modMenu) {
        List<Player> playerList = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);

            ItemStack icon = new ItemStack(Material.SKULL_ITEM);
            icon.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) icon.getItemMeta();

            meta.setOwner(p.getName());

            icon.setItemMeta(meta);

            modMenu.setOption(i, icon, p.getName(), "§7Moderation options for " + p.getName(), "§8§oUUID: " + p.getUniqueId());
        }
    }

    protected static void addModTools(IconMenu iconMenu, Player target) {
        ItemStack icon = new ItemStack(Material.SKULL_ITEM);
        icon.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) icon.getItemMeta();
        meta.setOwner(target.getName());
        icon.setItemMeta(meta);

        iconMenu.setOption(0, icon, "Managing " + target.getName());

        iconMenu.setOption(2, new ItemStack(Material.ENDER_PEARL), "Teleport To Player", "§8Teleport to the player.");
        iconMenu.setOption(3, new ItemStack(Material.EYE_OF_ENDER), "Bring Player", "§8Teleport the player to you.");

        if (Moderator.isPlayerMuted(target))
            iconMenu.setOption(4, new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "Unmute Player", "§8Unmute the specified player.");
        else
            iconMenu.setOption(4, new ItemStack(Material.STAINED_CLAY, 1, (short) 13), "Mute Player", "§8Mute the specified player.");

        iconMenu.setOption(5, new ItemStack(Material.STAINED_CLAY, 1, (short) 4), "Kick Player", "§8Kick the specified player.");

        if (Moderator.isPlayerBanned(target))
            iconMenu.setOption(6, new ItemStack(Material.STAINED_CLAY, 1, (short) 14), "Pardon Player", "§8Pardon the specified player.");
        else
            iconMenu.setOption(6, new ItemStack(Material.STAINED_CLAY, 1, (short) 13), "Ban Player", "§8Ban the specified player.");

        iconMenu.setOption(8, new ItemStack(Material.BOOK_AND_QUILL), "Exit");
    }

    private IconMenu getPlayerMenu(String target) {
        String title = "Mod Tools: " + target;

        if (title.length() > 32) {
            title = "MT: " + target;
        }

        return new IconMenu(title, 9, e -> {
            if (e.getPosition() == 0)
                return;

            if (e.getPosition() == 2) {
                e.getPlayer().teleport(e.getTarget());

                e.getPlayer().sendMessage("§6[Tyrant] Teleported to " + e.getTarget().getName());
            }

            if (e.getPosition() == 3) {
                e.getTarget().teleport(e.getPlayer());
            }

            if (e.getPosition() == 4) {
                if (Moderator.isPlayerMuted(e.getTarget())) {
                    Moderator.unmute(e.getTarget());

                    e.getPlayer().sendMessage("§6[Tyrant] Unmuted " + e.getTarget().getName() + ".");
                } else {
                    Moderator.mute(e.getTarget());

                    e.getPlayer().sendMessage("§6[Tyrant] Muted " + e.getTarget().getName() + ".");
                }

                e.setWillClose(true);
            } else if (e.getPosition() == 5) {
                Moderator.kick(e.getTarget());

                e.getPlayer().sendMessage("§6[Tyrant] Kicked " + e.getTarget().getName());

                e.setWillClose(true);
            } else if (e.getPosition() == 6) {
                if (Moderator.isPlayerBanned(e.getTarget())) {
                    Moderator.unban(e.getTarget());

                    e.getPlayer().sendMessage("§6[Tyrant] Pardoned " + e.getTarget().getName() + ".");
                } else {
                    Moderator.ban(e.getTarget());

                    e.getPlayer().sendMessage("§6[Tyrant] Banned " + e.getTarget().getName() + ".");
                }

                e.setWillClose(true);
            } else if (e.getPosition() == 8) {
                e.setWillClose(true);
            }

            e.setWillDestroy(true);
        }, JavaPlugin.getPlugin(Tyrant.class));
    }

    // Mute + ban convenience functions

    public static boolean isPlayerMuted(Player p) {
        return mutedPlayers.contains(p.getUniqueId());
    }

    public static boolean isPlayerBanned(Player p) {
        return bannedPlayers.contains(p.getUniqueId());
    }

    public static void mute(Player p) {
        mutedPlayers.add(p.getUniqueId());

        p.sendMessage("§6[Tyrant] You were muted by a moderator!");
    }

    public static void unmute(Player p) {
        mutedPlayers.remove(p.getUniqueId());

        p.sendMessage("§6[Tyrant] You are no longer muted.");
    }

    public static void kick(Player p) {
        p.kickPlayer("You were kicked by a moderator!");
    }

    public static void ban(Player p) {
        bannedPlayers.add(p.getUniqueId());

        p.kickPlayer("You were banned by a moderator!");
    }

    public static void unban(Player p) {
        Moderator.unban(p.getUniqueId());
    }

    public static void unban(UUID uuid) {
        bannedPlayers.remove(uuid);
    }
}
