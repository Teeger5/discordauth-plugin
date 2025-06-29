package trx.discordauth.authtypes.freezer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import trx.discordauth.Main;
import trx.discordauth.socketcomm.plugin.BotNotifier;

import java.util.UUID;

public class FreezerEventListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("Freezer listener on join");
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

	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player player) {
			if (!Main.getAuthorizedPlayers().contains(player.getUniqueId().toString())) {
				event.setCancelled(true);
			}
		}
	}
}
