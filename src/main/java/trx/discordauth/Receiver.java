package trx.discordauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;

import static trx.discordauth.ConfigValues.*;

public class Receiver {
	private final Map<SocketCommands, List<SocketTask>> socketCommandTasks;

	public Receiver() {
		socketCommandTasks = new HashMap<>();
	}

	public void registerSocketCommandTask(SocketCommands socketCommand, SocketTask task) {
		if (!socketCommandTasks.containsKey(socketCommand)) {
			socketCommandTasks.put(socketCommand, new ArrayList<>());
		}
		socketCommandTasks.get(socketCommand).add(task);
	}

	public void listen () {
		try (ServerSocket serverSocket = new ServerSocket(25567)) {
			var executorService = Executors.newSingleThreadExecutor();
			while (true) {
				try (Socket client = serverSocket.accept();
				     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

					String line = reader.readLine();
					if (line != null) {
						var parts = line.split(",");

						try {
							var command = SocketCommands.valueOf(parts[0]);
							var uuid = parts[1];
							var tasks = socketCommandTasks.get(command);
							if (tasks != null) {
								for (var task : tasks) {
									executorService.execute(() -> task.action(uuid));
								}
							}
						}
						catch (IllegalArgumentException e) {
							LOGGER.severe("Hiba: parancs -> SocketCommands: " + e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
