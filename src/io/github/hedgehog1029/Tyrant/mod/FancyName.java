package io.github.hedgehog1029.Tyrant.mod;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FancyName {

    private static final List<FancyColors> COLORS = Collections.unmodifiableList(Arrays.asList(FancyColors.values()));
    private static final int SIZE = COLORS.size();
    private static final Random RANDOM = new Random();

    public static ChatColor generateColor() {
        return COLORS.get(RANDOM.nextInt(SIZE)).getColor();
    }

    public enum FancyColors {
        DARK_BLUE(ChatColor.DARK_BLUE),
        DARK_AQUA(ChatColor.DARK_AQUA),
        DARK_GREEN(ChatColor.DARK_GREEN),
        BLUE(ChatColor.BLUE),
        DARK_PURPLE(ChatColor.DARK_PURPLE),
        RED(ChatColor.RED);

        private ChatColor color;

        FancyColors(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return this.color;
        }
    }
}
