package io.github.hedgehog1029.Tyrant.util;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import io.github.hedgehog1029.Tyrant.hooks.ProtocolLibHook;
import io.github.hedgehog1029.frame.annotations.Hook;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * ProtocolLib helper
 */
public class PacketHelper {
	@Hook
	private ProtocolLibHook protocolLib;

	public void sendPacket(Player p, PacketContainer pa) {
		ProtocolManager manager = this.getProtocolManager();

		if (manager == null) return;

		try {
			if (p == null)
				manager.broadcastServerPacket(pa);
			else
				manager.sendServerPacket(p, pa);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			// log dis
		}
	}

	public ProtocolManager getProtocolManager() {
		if (protocolLib == null)
			return null;

		return protocolLib.getProtocolManager();
	}
}
