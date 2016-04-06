package io.github.hedgehog1029.Tyrant.command;

import bsh.EvalError;
import bsh.Interpreter;
import com.sk89q.worldedit.bukkit.selections.Selection;
import io.github.hedgehog1029.Tyrant.hooks.WorldEditHook;
import io.github.hedgehog1029.frame.annotations.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class BeanShell {
    private HashMap<UUID, Interpreter> enviroments = new HashMap<>();
    private Interpreter consoleEnviroment;

    public BeanShell() {
        consoleEnviroment = new Interpreter();

        this.setup(consoleEnviroment, Bukkit.getConsoleSender());
        this.update(consoleEnviroment, Bukkit.getConsoleSender());
    }

    @Command(aliases = {"bsh", "beanshell"}, desc = "Interpret a BeanShell input.", usage = "/bsh <beanscript>")
    @Permission("tyrant.admin.beanshell")
    @HelpTopic("Tyrant")
    public void bsh(@Sender CommandSender sender, @Text String script) {
        try {
            Interpreter i = this.find(sender);

            Object obj = i.eval(script);

            if (obj != null) {
                sender.sendMessage("§eSuccessfully executed BeanShell.");
                sender.sendMessage("§eresult >>> " + String.valueOf(obj));
            } else {
                sender.sendMessage("§eSuccessfully executed BeanShell with no output.");
            }
        } catch (EvalError e) {
            sender.sendMessage("§cFailed to evaluate BeanShell code! Reason: " + e.getMessage());
        }
    }

    @Command(aliases = "bshfile", desc = "Read a BeanShell script.", usage = "/bshfile <path>")
    @Permission("tyrant.admin.beanshell")
    @HelpTopic("Tyrant")
    public void bshfile(@Sender CommandSender sender, @Text String path) {
        try {
            Interpreter interpreter = this.find(sender);

            Object obj = interpreter.source(path);

            if (obj != null) {
                sender.sendMessage("§eSuccessfully executed BeanShell.");
                sender.sendMessage("§eresult >>> " + String.valueOf(obj));
            } else {
                sender.sendMessage("§eSuccessfully executed BeanShell with no output.");
            }
        } catch (EvalError e) {
            sender.sendMessage("§cFailed to evaluate BeanShell script! Reason: " + e.getMessage());
        } catch (IOException e) {
            sender.sendMessage("§cFailed to read BeanShell script! Filepath might be wrong?");
        }
    }

    private Interpreter find(CommandSender sender) {
        Interpreter i;

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (!enviroments.containsKey(p.getUniqueId())) {
                i = new Interpreter();

                this.setup(i, p);
                this.update(i, p);

                enviroments.put(p.getUniqueId(), i);
            } else {
                this.update(enviroments.get(p.getUniqueId()), sender);

                i = enviroments.get(p.getUniqueId());
            }
        } else if (sender instanceof ConsoleCommandSender)
            i = consoleEnviroment;
        else i = new Interpreter();

        return i;
    }

    private void setup(Interpreter interpreter, CommandSender owner) {
        try {
            interpreter.set("out", Bukkit.getLogger());
            interpreter.set("server", Bukkit.getServer());
            interpreter.set("util", this);

            if (owner instanceof Player) {
                Player p = (Player) owner;

                interpreter.set("self", p);
            }
        } catch (EvalError e) {
            Bukkit.getLogger().info("§cError constructing initial BeanShell enviroment.");
        }
    }

    @Hook
    private WorldEditHook worldEditHook;

    private void update(Interpreter i, CommandSender sender) {
        try {

            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (worldEditHook != null) {
                    Selection selection = worldEditHook.getSelectionOf(p);

                    if (selection != null) {
                        i.set("selection", selection);
                    }
                }

                i.set("loc", p.getLocation());
            }

            i.set("worlds", Bukkit.getServer().getWorlds());
        } catch (EvalError e) {
            sender.sendMessage("§cThere was an error constructing your personal BSH enviroment.");
        }
    }
}
