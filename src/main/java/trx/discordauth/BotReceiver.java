package trx.discordauth;

import org.bukkit.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;

import static trx.discordauth.ConfigValues.*;

public class BotReceiver implements Runnable {
	private Server server;
	private Set<String> authorizedUserUUIDs;

	public BotReceiver(Server server, Set<String> authorizedUserUUIDs) {
		this.server = server;
		this.authorizedUserUUIDs = authorizedUserUUIDs;
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(25567)) {
			while (true) {
				try (Socket client = serverSocket.accept();
				     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
					String line = reader.readLine();
					if (line != null) {
						if (line.startsWith(AUTH_SUCCESS)){
							String uuidStr = line.split(",")[1];
							var player = server.getPlayer(UUID.fromString(uuidStr));
							if (player != null) {
								authorizedUserUUIDs.add(uuidStr);
								player.sendMessage("Hitelesítés kész, jó játékot!");
							}
						}
						else if (line.startsWith(AUTH_NOT_FOUND)) {
							String uuidStr = line.split(",")[1];
							var player = server.getPlayer(UUID.fromString(uuidStr));
							if (player != null) {
								player.sendMessage("");
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
