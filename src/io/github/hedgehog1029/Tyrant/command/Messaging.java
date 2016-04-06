package io.github.hedgehog1029.Tyrant.command;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.Sender;
import io.github.hedgehog1029.frame.annotations.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Messaging {

    private HashMap<String, String> conversations = new HashMap<>();

    @Command(aliases = {"tell", "msg"}, desc = "Message another player privately!", usage = "/msg <player> <msg>")
    public void sendMessage(@Sender CommandSender sender, Player target, @Text String msg) {
        this.send(sender, target, msg);
    }

    @Command(aliases = {"reply", "r"}, desc = "Reply to your last conversation.", usage = "/r <msg>")
    public void reply(@Sender CommandSender sender, @Text String msg) {
        if (!conversations.containsKey(sender.getName())) {
            sender.sendMessage("§6[Tyrant] §cNo previous conversation! Use /msg first.");
            return;
        }

        if (conversations.get(sender.getName()).equalsIgnoreCase("CONSOLE")) {
            sender.sendMessage("§6[§eyou §6-> §cCONSOLE§6] " + msg);
            Bukkit.getConsoleSender().sendMessage("§6[§r" + sender.getName() + " §6-> §eyou§6] " + msg);
            return;
        }

        Player target = Bukkit.getServer().getPlayer(conversations.get(sender.getName()));

        if (target == null ) {
            sender.sendMessage("§6[Tyrant] §cPrevious target couldn't be found! Did they log off?");
        } else
            this.send(sender, target, msg);
    }

    public void send(CommandSender sender, Player target, String msg) {
        sender.sendMessage("§6[§eyou §6-> §r" + target.getDisplayName() + "§6] " + msg);
        conversations.put(sender.getName(), target.getName());

        if (sender instanceof Player) {
            Player psender = (Player) sender;
            target.sendMessage("§6[§r" + psender.getDisplayName() + " §6-> §eyou§6] " + msg);
        } else {
            target.sendMessage("§6[§c" + sender.getName() + " §6-> §eyou§6] " + msg);
        }

        conversations.put(target.getName(), sender.getName());
    }
}
