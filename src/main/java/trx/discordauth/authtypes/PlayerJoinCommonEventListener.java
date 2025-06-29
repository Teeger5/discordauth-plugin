package trx.discordauth.authtypes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import trx.discordauth.ConfigValues;
import trx.discordauth.authtypes.authverse.Authverse;
import trx.discordauth.socketcomm.plugin.BotNotifier;

public class PlayerJoinCommonEventListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		BotNotifier.sendUUIDToAuth(event.getPlayer().getUniqueId());

		event.getPlayer().sendMessage("Üdv újra! A folytatáshoz hitelesítsd magad " +
				"Discordon a bot által küldött üzeneten lévő gomb megnyomásával");

		if (!event.getPlayer().getWorld().getName().equals(ConfigValues.AUTH_WORLD_NAME)) {
			Authverse.saveLogoutLocation(event.getPlayer());
		}
	}
}
