package trx.discordauth;

import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.authtypes.AuthType;
import trx.discordauth.authtypes.PlayerJoinCommonEventListener;

import java.util.HashSet;
import java.util.Set;
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
		getServer().getPluginManager().registerEvents(new PlayerJoinCommonEventListener(), this);
		try {
			var authType = AuthType.parseConfigValue(ConfigValues.AUTH_TYPE);
			getServer().getPluginManager().registerEvents(authType.getEventListener(), this);
			new Thread(() ->
					authType.getReceiver(getServer()).listen(ConfigValues.PLUGIN_PORT)
			).start();
		}
		catch (Exception e) {
			getLogger().severe("Hiba a htielesítés beállításakor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("DiscordAuth has been disabled!");
	}

	public static Set<String> getAuthorizedPlayers() {
		return AUTHORIZED_PLAYERS;
	}
}