package trx.discordauth;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ConfigValues {
	public static String BOT_HOST;
	public static int BOT_PORT;

	/** Ezt használja a bot a hitelesítési kérés üzenetek azonosíására */
	public static String AUTH_REQUEST;

	/** Ezt használja a bot a sikeres belépés üzenetek azonosíására */
	public static String AUTH_SUCCESS;

	/** Ezt használja a bot a nem ismert felhasználó üzenetek azonosíására */
	public static String AUTH_NOT_FOUND;

	/** Ezt használja a bot a hitelesítési folyamat elkezdődött üzenetek azonosíására */
	public static String AUTH_INITIATED;

	public static Logger LOGGER;

	/**
	 * Adatok betöltése a konfigurációból:
	 * - Bot host
	 * - Bot port
	 * - Hitelesítési kérés üzenet azonosítója
	 * - Sikeres belépés üzenet azonosítója
	 * Ezt meg kell hívni a plugin indulásakor, hogy üzeneteket tudjon küldeni a botnak
	 * @param plugin referencia a fő osztályra
	 */
	public static void initialize(JavaPlugin plugin) {
		FileConfiguration config = plugin.getConfig();
		BOT_HOST = config.getString("bot.host", "localhost");
		BOT_PORT = config.getInt("bot.port", 25566);
		AUTH_REQUEST = config.getString("messages.authRequest");
		AUTH_SUCCESS = config.getString("messages.authSuccess");
		AUTH_NOT_FOUND = config.getString("messages.authNotFound");
		LOGGER = plugin.getLogger();
		LOGGER.info(String.format("Bot elérhetősége: %s:%d", BOT_HOST, BOT_PORT));
	}
}

