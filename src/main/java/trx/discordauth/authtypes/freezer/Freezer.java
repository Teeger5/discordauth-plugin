package trx.discordauth.authtypes.freezer;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.Main;
import trx.discordauth.authtypes.AuthMethod;
import trx.discordauth.socketcomm.shared.Receiver;
import trx.discordauth.socketcomm.shared.SocketCommand;

import java.util.UUID;

public class Freezer implements AuthMethod {
	@Override
	public Listener getEventListener() {
		return new FreezerEventListener();
	}

	@Override
	public Receiver getReceiver(JavaPlugin plugin) {
		var receiver = new Receiver();

		receiver.registerSocketCommandTask(SocketCommand.AUTH_SUCCESS, uuid -> {
			var player = Bukkit.getPlayer(UUID.fromString(uuid));
			if (player != null) {
				Main.getAuthorizedPlayers().add(uuid);
				player.sendMessage("Hitelesítés kész, jó játékot!");
			}
		});

		receiver.registerSocketCommandTask(SocketCommand.USER_NOT_FOUND, uuid -> {
			var player = Bukkit.getPlayer(UUID.fromString(uuid));
			if (player != null) {
				player.sendMessage("A játék megkezdéséhez előbb regisztrálnod kell a .. helyen.");
			}
		});

		receiver.registerSocketCommandTask(SocketCommand.AUTH_INITIATED, uuid -> {
			var player = Bukkit.getPlayer(UUID.fromString(uuid));
			if (player != null) {
				player.sendMessage("A bot üzenetet küldött neked Discordon. Kattints a rajta lévő gombra a hitelesítéshez!");
			}
		});

		return receiver;
	}
}
