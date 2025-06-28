package trx.discordauth;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

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
		getLogger().severe("Discord Auth Plugin Enabled!");
		var logger = Logger.getLogger("Minecraft");
		saveDefaultConfig();
		ConfigValues.initialize(this);
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		logger.warning("DiscordAuth betöltve");
		new Thread(() -> initBotReceiver(getServer()).listen()).start();
	}

	@Override
	public void onDisable() {
		getLogger().info("DiscordAuth has been disabled!");
	}

	public static Set<String> getAuthorizedPlayers() {
		return AUTHORIZED_PLAYERS;
	}

	private Receiver initBotReceiver (Server server) {
		var receiver = new Receiver();

		receiver.registerSocketCommandTask(SocketCommands.AUTH_SUCCESS, uuid -> {
			var player = getServer().getPlayer(UUID.fromString(uuid));
			if (player != null) {
				getAuthorizedPlayers().add(uuid);
				player.sendMessage("Hitelesítés kész, jó játékot!");
			}
		});

		receiver.registerSocketCommandTask(SocketCommands.AUTH_USER_NOT_FOUND, uuid -> {
			var player = getServer().getPlayer(UUID.fromString(uuid));
			if (player != null) {
				player.sendMessage("A játék megkezdéséhez előbb regisztrálnod kell a .. helyen.");
			}
		});

		return receiver;
	}
}