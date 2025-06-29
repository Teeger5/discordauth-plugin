package trx.discordauth;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.socketcomm.plugin.ConfigValues;
import trx.discordauth.socketcomm.shared.Receiver;
import trx.discordauth.socketcomm.shared.SocketCommand;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

	public static Logger LOGGER;

	private static Set<String> AUTHORIZED_PLAYERS = new HashSet<>();

	@Override
	public void onEnable() {
		LOGGER = getLogger();
		getLogger().info("Discord Auth Plugin Enabled!");
		saveDefaultConfig();
		ConfigValues.initialize(this);
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		new Thread(() -> initBotReceiver(getServer()).listen(ConfigValues.PLUGIN_PORT)).start();
	}

	@Override
	public void onDisable() {
		getLogger().info("DiscordAuth has been disabled!");
	}

	public static Set<String> getAuthorizedPlayers() {
		return AUTHORIZED_PLAYERS;
	}

	/**
	 * Bot felől érkező parancsokra történő műveletek beállítása
	 * @return az elkészült Receiver példány
	 */
	private Receiver initBotReceiver (S) {
		var receiver = new Receiver();

		receiver.registerSocketCommandTask(SocketCommand.AUTH_SUCCESS, uuid -> {
			var player = getServer().getPlayer(UUID.fromString(uuid));
			if (player != null) {
				getAuthorizedPlayers().add(uuid);
				player.sendMessage("Hitelesítés kész, jó játékot!");
			}
		});

		receiver.registerSocketCommandTask(SocketCommand.USER_NOT_FOUND, uuid -> {
			var player = getServer().getPlayer(UUID.fromString(uuid));
			if (player != null) {
				player.sendMessage("A játék megkezdéséhez előbb regisztrálnod kell a .. helyen.");
			}
		});

		receiver.registerSocketCommandTask(SocketCommand.AUTH_INITIATED, uuid -> {
			var player = getServer().getPlayer(UUID.fromString(uuid));
			if (player != null) {
				player.sendMessage("A bot üzenetet küldött neked Discordon. Kattints a rajta lévő gombra a hitelesítéshez!");
			}
		});

		return receiver;
	}
}