package io.github.hedgehog1029.Tyrant.mod;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FancyName {

    private static final ChatColor[] COLORS = {
            ChatColor.DARK_BLUE,
            ChatColor.BLUE,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_PURPLE,
            ChatColor.RED
    };

    private static final int SIZE = COLORS.length;
    private static final Random RANDOM = new Random();

    public static ChatColor generateColor() {
        return COLORS[RANDOM.nextInt(SIZE)];
    }
}
