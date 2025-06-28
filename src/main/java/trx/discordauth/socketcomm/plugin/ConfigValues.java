package trx.discordauth.socketcomm.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigValues {
	public static String BOT_HOST;
	public static int BOT_PORT;
	public static int PLUGIN_PORT;

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
	}
}

