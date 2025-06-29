package trx.discordauth.authtypes.authverse;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import trx.discordauth.ConfigValues;

public class AuthverseEventListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("Authverse event listener on join");
		var authWorld = Bukkit.getWorld(ConfigValues.AUTH_WORLD_NAME);
		event.getPlayer().teleport(authWorld.getSpawnLocation());
	}
}
