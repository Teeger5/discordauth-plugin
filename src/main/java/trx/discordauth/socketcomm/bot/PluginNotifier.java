package trx.discordauth.socketcomm.bot;

import trx.discordauth.socketcomm.shared.SocketCommand;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Üzenetek küldése a plugin-nak
 * FONTOS: Be kell állítani a használat előbb
 * a "PLUGIN_HOST" és a "PLUGIN_PORT" környezeti változókat
 */
public class PluginNotifier {
	private static Logger log = Logger.getLogger(PluginNotifier.class.getName());

	/**
	 * Plugin értesítése sikeres hitelesítésről
	 * @param uuid hitelesített jateékos UUID
	 */
	public static void notifyAuthSuccess(String uuid) {
		notifyPlugin(SocketCommand.AUTH_SUCCESS, uuid);
	}

	/**
	 * Plugin értesítése nem található felhasználóról
	 * @param uuid játékos UUID
	 */
	public static void notifyAuthUserNotFound(String uuid) {
		notifyPlugin(SocketCommand.USER_NOT_FOUND, uuid);
	}

	public static void notifyAuthUserFound(String uuid) {
		notifyPlugin(SocketCommand.USER_FOUND, uuid);
	}

	public static void notifyAuthInitiated(String uuid) {
		notifyPlugin(SocketCommand.AUTH_INITIATED, uuid);
	}

	/**
	 * Socketen keresztül elküldi a parancsot és az UUID-et a pluginnak,
	 * @param command parancs a pluginnak
	 * @param uuid felhasználó UUID
	 */
	private static void notifyPlugin(SocketCommand command, String uuid) {
		var host = System.getProperty("PLUGIN_HOST");
		var port = Integer.parseInt(System.getProperty("PLUGIN_PORT"));
		try (Socket socket = new Socket(host,  port);
		     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

			var message = String.format("%s,%s", command, uuid);
			out.println(message);
			log.fine("Hitelesítés sikeres üzenet elküldve a plugin-nak: " + message);
		} catch (Exception e) {
			log.severe(String.format("Hiba a plugin értesítésekor: command: %s, UUID: %s", command, uuid));
			e.printStackTrace();
		}
	}
}
