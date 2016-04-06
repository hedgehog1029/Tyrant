package io.github.hedgehog1029.Tyrant.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import io.github.hedgehog1029.Tyrant.Tyrant;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Title {
	private String title;
	private String subtitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;

	public Title(String title) {
		this(title, null, 1, 5, 1);
	}

	public Title(String title, String subtitle) {
		this(title, subtitle, 1, 5, 1);
	}

	public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTimings(int fadeIn, int stay, int fadeOut) {
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	/**
	 * Send the title to a player. If no player is specified, broadcast instead. Silently fails if ProtocolLib not present.
	 * @param p The player to send to. If null, broadcast.
	 */
	public void send(Player p) {
		Bukkit.getServer().getScheduler().runTaskAsynchronously(JavaPlugin.getPlugin(Tyrant.class), () -> sendBlocking(p));
	}

	private void sendBlocking(Player p) {
		PacketHelper helper = (PacketHelper) ModuleLoader.getInstance(PacketHelper.class);

		if (helper == null) {
			Bukkit.getLogger().warning("[Tyrant] PacketHelper is null (???)");
			return; // should never really be null but oh well
		}

		ProtocolManager manager = helper.getProtocolManager();

		if (manager == null) {
			Bukkit.getLogger().warning("[Tyrant] ProtocolManager is null.");
			return;
		}

		PacketContainer titlePacket = manager.createPacket(PacketType.Play.Server.TITLE);

		titlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.RESET);

		helper.sendPacket(p, titlePacket);

		titlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
		titlePacket.getIntegers()
				.write(0, fadeIn * 20)
				.write(1, stay * 20)
				.write(2, fadeOut * 20);

		helper.sendPacket(p, titlePacket);

		if (subtitle != null && !subtitle.equals("")) {
			titlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
			titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));

			helper.sendPacket(p, titlePacket);
		}

		titlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
		titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(title));

		helper.sendPacket(p, titlePacket);
	}
}
