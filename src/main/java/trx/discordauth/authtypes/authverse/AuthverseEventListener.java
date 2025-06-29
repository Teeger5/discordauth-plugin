package trx.discordauth.authtypes.authverse;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import trx.discordauth.ConfigValues;

public class AuthverseEventListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (!event.getPlayer().getWorld().getName().equals(ConfigValues.AUTH_WORLD_NAME)) {
			Authverse.saveLogoutLocation(event.getPlayer());
		}
	}
}
