package trx.discordauth;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.authtypes.AuthType;

public class ConfigValues {
	public static String BOT_HOST;
	public static int BOT_PORT;
	public static int PLUGIN_PORT;
	public static String AUTH_WORLD_NAME;
	public static String AUTH_TYPE;

	/**
	 * Adatok betöltése a konfigurációból:
	 * - Bot host
	 * - Bot port
	 * - Plugin port
	 * Ezt meg kell hívni a plugin indulásakor, hogy üzeneteket tudjon küldeni a botnak
	 * @param plugin referencia a fő osztályra
	 */
	public static void initialize(JavaPlugin plugin) {
		FileConfiguration config = plugin.getConfig();
		BOT_HOST = config.getString("bot.host", "localhost");
		BOT_PORT = config.getInt("bot.port", 25566);
		PLUGIN_PORT = config.getInt("plugin.port", 25567);
		plugin.getLogger().info(String.format("Bot elérhetősége: %s:%d", BOT_HOST, BOT_PORT));

		AUTH_WORLD_NAME = config.getString("auth.world_name", "auth_world");
		AUTH_TYPE = config.getString("auth.type", "freezer");
		plugin.getLogger().info(String.format("Auth Type: %s, world name: %s", AUTH_TYPE,  AUTH_WORLD_NAME));
	}
}

