package trx.discordauth.authtypes;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import trx.discordauth.authtypes.authverse.Authverse;
import trx.discordauth.authtypes.freezer.Freezer;
import trx.discordauth.socketcomm.shared.Receiver;

public enum AuthType {

	/** A játékos megfagy csatlakozáskor, és kiolvad hitelesítés után */
	FREEZER(new Freezer()),

	/** A játékos csatlakozáskor átkerül egy várakozásra szolgáló világba,
	 * és hitelesítés után kerül vissza oda, ahol kilépéskor volt
	 */
	AUTHVERSE(new Authverse());

	private AuthMethod methodInstance;

	AuthType(AuthMethod methodInstance) {
		this.methodInstance = methodInstance;
		methodInstance.setup();
	}

	public Listener getEventListener () {
		return methodInstance.getEventListener();
	}

	public Receiver getReceiver (Server server) {
		return methodInstance.getReceiver(server);
	}

	/**
	 * A config.yml-ben megadott hitelesítési módszer felismerése
	 * Érvényes értékek:
	 * - freezer
	 * - authworld
	 * @param name név a config.yml-ben
	 * @return megfelelő enum érték
	 */
	public static AuthType parseConfigValue(String name) {
		return  switch (name) {
			case "freezer" -> FREEZER;
			case "authworld" -> AUTHVERSE;
			default -> throw new IllegalArgumentException("Érvénytelen hitelesítési módszer név: " + name);
		};
	}
}
