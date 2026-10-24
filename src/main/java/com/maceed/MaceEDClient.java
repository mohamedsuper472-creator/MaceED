package com.maceed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MaceEDClient implements ClientModInitializer {
	private int lastVersion = -1;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) {
				lastVersion = -1;
				return;
			}
			if (lastVersion != HitboxConfig.version) {
				lastVersion = HitboxConfig.version;
				client.player.refreshDimensions();
			}
		});
	}
}
