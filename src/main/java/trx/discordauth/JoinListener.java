package trx.discordauth;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();

		BotNotifier.sendUUIDToAuth(uuid);

		event.getPlayer().sendMessage("Üdv újra! A folytatáshoz hitelesítsd magad " +
				"Discordon a bot által küldött üzeneten lévő gomb megnyomásával");
	}


	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Main.getAuthorizedPlayers().remove(event.getPlayer().getUniqueId().toString());
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!Main.getAuthorizedPlayers().contains(event.getPlayer().getUniqueId().toString())) {
			event.setCancelled(true);
		}
	}
}
