package trx.discordauth.socketcomm.shared;

public enum SocketCommand {

	// Plugin küldi, bot fogadja

	/** A játékos hitelesítési kérése a bothoz */
	AUTH_REQUEST,

	/** A plugin továbbengedte a játékost sikeres hitelesítés után */
	AUTH_CONFIRM_SUCCESS,

	// Bot küldi, plugin fogadja

	/** A játékoshoz tartozik Discord ID */
	USER_FOUND,

	/** A játékoshoz nem tartozik Discord ID, azaz nem ismert */
	USER_NOT_FOUND,

	/** A bot elküldte a hitelesítési üzenetet a felhasználónak */
	AUTH_INITIATED,

	/** A felhasználó nem kattintott rá időben a hitelesítési gombra */
	AUTH_TIMED_OUT,

	/** A felhasználó megnyomta a hitelesítési üzeneten a gombot */
	AUTH_SUCCESS,

}
