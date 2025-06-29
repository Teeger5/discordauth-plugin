package trx.discordauth.authtypes.authverse;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import trx.discordauth.ConfigValues;
import trx.discordauth.Main;
import trx.discordauth.authtypes.AuthMethod;
import trx.discordauth.socketcomm.shared.Receiver;
import trx.discordauth.socketcomm.shared.SocketCommand;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Authverse implements AuthMethod {
	/** Játékosok kilépési helyzetének adatait tároló fájl neve */
	private static final String locationsYmlName = "player_locations.yml";
	private static FileConfiguration locations;

	private static final String VAR_WORLD = ".world";
	private static final String VAR_X = ".x";
	private static final String VAR_Y = ".y";
	private static final String VAR_Z = ".z";
	private static final String VAR_YAW = ".yaw";
	private static final String VAR_PITCH = ".pitch";

	public Listener getEventListener () {
		return new AuthverseEventListener();
	}

	@Override
	public Receiver getReceiver(JavaPlugin plugin) {
		var receiver = new Receiver();

		receiver.registerSocketCommandTask(SocketCommand.AUTH_SUCCESS, uuid -> {
			var player = Bukkit.getPlayer(UUID.fromString(uuid));
			if (player != null) {
				Bukkit.getScheduler().runTask(plugin, () -> {

					var location = getLogoutLocation(player.getUniqueId());
					player.sendMessage("Hitelesítés kész, jó játékot!");
					if (location != null) {
						player.teleport(location);
					}
					else {
						var spaawn = Bukkit.getWorlds().get(0).getSpawnLocation();
						player.teleport(spaawn);
					}
				});
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

	/** Hitelesítési várakozó világ létrehozása, ha még nem létezik */
	public void  setup() {
		locations = YamlConfiguration.loadConfiguration(new File(locationsYmlName));
		var worldFolder = new File(Bukkit.getWorldContainer(), ConfigValues.AUTH_WORLD_NAME);
		if (worldFolder.exists() && Bukkit.getWorld(ConfigValues.AUTH_WORLD_NAME) != null) {
			return;
		}
		Bukkit.getLogger().info("WGenerating world");
		var worldCreator = new WorldCreator(ConfigValues.AUTH_WORLD_NAME);
		worldCreator.type(WorldType.FLAT);
		var world = worldCreator.createWorld();
		Bukkit.getWorlds().add(world);
	}

	public static Location getLogoutLocation (UUID playerUUID) {
		String uuid = playerUUID.toString();

		if (!locations.contains(uuid)) return null;

/*		String worldName = locations.getString(uuid + VAR_WORLD);
		World world = Bukkit.getWorld(worldName);

		double x = locations.getDouble(uuid + VAR_X);
		double y = locations.getDouble(uuid + VAR_Y);
		double z = locations.getDouble(uuid + VAR_Z);
		float yaw = (float) locations.getDouble(uuid + VAR_YAW);
		float pitch = (float) locations.getDouble(uuid + VAR_PITCH);

		return new Location(world, x, y, z, yaw, pitch);
*/
		return new  Location(
				Bukkit.getWorld(locations.getString(uuid + VAR_WORLD)),
				locations.getDouble(uuid + VAR_X),
				locations.getDouble(uuid + VAR_Y),
				locations.getDouble(uuid + VAR_Z),
				(float) locations.getDouble(uuid + VAR_YAW),
				(float) locations.getDouble(uuid + VAR_PITCH)
		);
	}

	public static void saveLogoutLocation (Player player) {
		var loc =  player.getLocation();
		var world = player.getWorld().getName();
		var uuid = player.getUniqueId().toString();

		locations.set(uuid + VAR_WORLD, loc.getWorld().getName());
		locations.set(uuid + VAR_X, loc.getX());
		locations.set(uuid + VAR_Y, loc.getY());
		locations.set(uuid + VAR_Z, loc.getZ());
		locations.set(uuid + VAR_YAW, loc.getYaw());
		locations.set(uuid + VAR_PITCH, loc.getPitch());

		try {
			locations.save(locationsYmlName);
		}
		catch (IOException e) {
			Main.LOGGER.severe(String.format("Hiba a '%s' játékos kilépési helyének mentésekor: %s", player.getName(),  e.getMessage()));
			e.printStackTrace();
		}
	}
}
