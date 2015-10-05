package io.github.hedgehog1029.Tyrant.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

public class SleepListener implements Listener {

    ArrayList<UUID> playersSleeping = new ArrayList<>();

    private final int THRESHOLD = 50;

    @EventHandler
    public void playerSleepEvent(PlayerBedEnterEvent e) {
        e.getPlayer().setBedSpawnLocation(e.getBed().getLocation());
        e.getPlayer().sendMessage("§6[Tyrant] You have set your spawn locaion to this bed. When you die, you will respawn here.");

        playersSleeping.add(e.getPlayer().getUniqueId());

        this.updateSleeping(e.getPlayer().getWorld());
    }

    @EventHandler
    public void leaveBed(PlayerBedLeaveEvent e) {
        if (e.getPlayer().getWorld().getTime() < 13000 || !playersSleeping.contains(e.getPlayer().getUniqueId()))
            return;

        playersSleeping.remove(e.getPlayer().getUniqueId());

        this.updateSleeping(e.getPlayer().getWorld());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {
        if (e.getPlayer().getWorld().getTime() < 13000 || !playersSleeping.contains(e.getPlayer().getUniqueId()))
            return;

        playersSleeping.remove(e.getPlayer().getUniqueId());

        this.updateSleeping(e.getPlayer().getWorld());
    }

    public void updateSleeping(World w) {
        int sleeping = playersSleeping.size();
        int online = Bukkit.getServer().getOnlinePlayers().size();

        float percent = (sleeping * 100.0f) / online;

        Bukkit.getServer().broadcastMessage("§6[Tyrant] There are now " +
                sleeping + "/" + online + " players sleeping. (" + percent + "%)");

        if (percent >= THRESHOLD) {
            Bukkit.getServer().broadcastMessage("§6[Tyrant] Setting time to day.");
            w.setTime(0);

            playersSleeping.clear();
        }
    }
}
