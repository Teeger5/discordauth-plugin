package trx.discordauth;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import static trx.discordauth.ConfigValues.*;

public class BotNotifier {

	/**
	 * Játékos UUID küldése a botnak a hitelesítés megkezdéséhez
	 * @param playerUUID játékos UUID
	 */
	public static void sendUUIDToAuth(UUID playerUUID) {
		sendMessage(AUTH_REQUEST + "," + playerUUID);
	}

	/**
	 * Visszajelzés küldése a botnak a játékos sikeres továbbengedéséről
	 * @param playerUUID játékos UUID
	 */
	public static void sendAuthSuccessConfirmation(UUID playerUUID) {
		sendMessage(AUTH_REQUEST + "," + playerUUID);
	}

	/**
	 * Üzenet küldése a botnak
	 * Az üzenet Socket használatával lesz küldve
	 * A host és a port a config.yml fájlban beállítható
	 * @param message üzenet a botnak
	 */
	private static void sendMessage(String message) {
		try (Socket socket = new Socket(BOT_HOST, BOT_PORT);
		     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

			out.println(message);
			LOGGER.fine("Üzenet -> bot: " + message);
		} catch (Exception e) {
			LOGGER.severe("Hiba az üzenet küldésekor: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
