package io.github.hedgehog1029.Tyrant.hooks;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import io.github.hedgehog1029.frame.hook.IPluginHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldEditHook implements IPluginHook {
    public WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }

    public Selection getSelectionOf(Player p) {
        return this.getWorldEdit().getSelection(p);
    }

    @Override
    public boolean available() {
        return Bukkit.getPluginManager().isPluginEnabled("WorldEdit");
    }
}
