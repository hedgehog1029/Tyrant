package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.frame.loader.Command;
import io.github.hedgehog1029.frame.loader.Sender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryViewer {
    @Command(aliases = {"inv", "viewinv"}, desc = "View the inventory of <target>.", usage = "/viewinv <Player>", permission = "tyrant.admin.viewinv", helpTopic = "Tyrant")
    public void viewInventory(@Sender Player p, Player target) {
        Inventory i = target.getInventory();

        p.openInventory(i);

        p.sendMessage("§6[Tyrant] Opened " + target.getName() + "'s inventory.");
    }
}
