package trx.discordauth;

public enum SocketCommands {

	/** A játékos hitelesítési kérése a bothoz */
	AUTH_REQUEST,

	/** A felhasználó megnyomta a hitelesítési üzeneten a gombot */
	AUTH_SUCCESS,

	/** A játékoshoz tartozik Discord ID */
	AUTH_USER_FOUND,

	/** A játékoshoz nem tartozik Discord ID, azaz nem ismert */
	AUTH_USER_NOT_FOUND,

	/** A bot elküldte a hitelesítési üzenetet a felhasználónak */
	AUTH_INITIATED,
}
