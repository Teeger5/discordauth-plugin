package trx.discordauth.authtypes;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.socketcomm.shared.Receiver;

public interface AuthMethod {

	/** EventListener kérése a hitelesítési módszerhez */
	Listener getEventListener ();

	/**
	 * Bot felől érkező parancsok fogadása
	 * @param server getServer() értéke
	 * @return az elkészült Receiver példány
	 */
	Receiver getReceiver(JavaPlugin plugin);

	/** Műveletek elvégzése, ha szükséges */
	default void setup() {}
}
