package trx.discordauth.socketcomm.shared;

/**
 * Feladat leírása, amiket a Receiver hajt végre adott parancs érkezésekor
 */
public interface SocketTask {

	/**
	 * Parancs érkezésekor lesz végrehajtva ez a metódus
	 * @param uuid beérkezett játékos UUID
	 */
	void action (String uuid);
}
