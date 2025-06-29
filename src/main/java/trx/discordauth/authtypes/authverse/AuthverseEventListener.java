package trx.discordauth.authtypes.authverse;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import trx.discordauth.ConfigValues;

public class AuthverseEventListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
//		event.getPlayer().sendMessage("Authverse event listener on join");
		var authWorld = Bukkit.getWorld(ConfigValues.AUTH_WORLD_NAME);
/*		Bukkit.getLogger().info("Available worlds: ");
		for (World w : Bukkit.getWorlds()) {
			Bukkit.getLogger().info("\t" + w.getName());
		}
		Bukkit.getLogger().info("Auth world name config: " + ConfigValues.AUTH_WORLD_NAME);
		Bukkit.getLogger().info("Auth world: " + authWorld);
		Bukkit.getLogger().info("Auth World spawn: " + authWorld.getSpawnLocation());*/
		event.getPlayer().teleport(authWorld.getSpawnLocation());
	}

	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event) {
		if (event.getEntity().getWorld().getName().equals(ConfigValues.AUTH_WORLD_NAME)) {
			event.setCancelled(true);
		}
	}
}
