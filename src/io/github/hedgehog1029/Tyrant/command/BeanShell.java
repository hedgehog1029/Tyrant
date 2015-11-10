package io.github.hedgehog1029.Tyrant.command;

import bsh.EvalError;
import bsh.Interpreter;
import io.github.hedgehog1029.frame.loader.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class BeanShell {
    private Interpreter interpreter;

    public BeanShell() {
        try {
            interpreter = new Interpreter();

            interpreter.set("out", Bukkit.getLogger());
            interpreter.set("server", Bukkit.getServer());
            interpreter.set("worlds", Bukkit.getServer().getWorlds());
            interpreter.set("util", this);
        } catch (EvalError evalError) {
            Bukkit.getLogger().info("§c[Tyrant] Error constructing BeanShell enviroment! This is kind of bad.");
        }
    }

    @Command(aliases = {"bsh", "beanshell"}, desc = "Interpret a BeanShell input.", usage = "/bsh <beanscript>")
    @Permission("tyrant.admin.beanshell")
    @HelpTopic("Tyrant")
    public void bsh(@Sender CommandSender sender, @Text String script) {
        try {
            Object obj = interpreter.eval(script);

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
            Object obj = interpreter.source(path);

            if (obj != null) {
                sender.sendMessage("§eSuccessfully executed BeanShell.");
                sender.sendMessage("§eresult >>> " + String.valueOf(obj));
            } else {
                sender.sendMessage("§eSuccessfully executed BeanShell with no output.");
            }
        } catch (EvalError e) {
            sender.sendMessage("Failed to evaluate BeanShell script! Reason: " + e.getMessage());
        } catch (IOException e) {
            sender.sendMessage("Failed to read BeanShell script! Filepath might be wrong?");
        }
    }
}
