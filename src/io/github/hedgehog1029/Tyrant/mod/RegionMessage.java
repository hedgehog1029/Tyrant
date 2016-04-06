package io.github.hedgehog1029.Tyrant.mod;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.hedgehog1029.Tyrant.Tyrant;
import io.github.hedgehog1029.Tyrant.hooks.WorldGuardHook;
import io.github.hedgehog1029.Tyrant.util.Title;
import io.github.hedgehog1029.frame.annotations.Hook;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RegionMessage implements Listener {
	@Hook
	private WorldGuardHook worldGuard;

	private HashMap<UUID, List<ProtectedRegion>> regionsVisited = new HashMap<>();

	@EventHandler
	public void move(PlayerMoveEvent event) {
		if (worldGuard == null) return;

		Player player = event.getPlayer();

		if (!regionsVisited.containsKey(player.getUniqueId()))
			regionsVisited.put(player.getUniqueId(), new ArrayList<>());

		RegionContainer container = worldGuard.getWorldGuard().getRegionContainer();
		RegionManager manager = container.get(player.getWorld());
		if (manager == null) return;

		ApplicableRegionSet regions = manager.getApplicableRegions(event.getTo());
		List<ProtectedRegion> visited = regionsVisited.get(player.getUniqueId());

		try {
			for (ProtectedRegion region : regions.getRegions()) {
				if (!visited.contains(region)) {
					// i've never seen this place before in my life... or maybe in 30 seconds
					visited.add(region);
					Bukkit.getScheduler().runTaskLaterAsynchronously(JavaPlugin.getPlugin(Tyrant.class), () -> enter(player, region), 1);
				}
			}

			Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Tyrant.class), () -> {
				for (ProtectedRegion region2 : visited) {
					if (!regions.getRegions().contains(region2)) {
						// leaving the tavern
						visited.remove(region2);
						Bukkit.getScheduler().runTaskLaterAsynchronously(JavaPlugin.getPlugin(Tyrant.class), () -> exit(player), 1);
					}
				}
			}, 1);
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
	}

	public static void enter(Player player, ProtectedRegion region) {
		String name = WordUtils.capitalize(region.getId());

		Title title = new Title("§3" + name, null, 1, 5, 1);

		title.send(player);
	}

	public static void exit(Player player) {
		Title title = new Title("§2Wilderness", "§2Monsters abound!", 1, 5, 1);

		title.send(player);
	}
}
