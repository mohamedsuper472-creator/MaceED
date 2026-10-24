package com.maceed;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaceEDMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("maceed");

	@Override
	public void onInitialize() {
		HitboxConfig.load();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
			dispatcher.register(Commands.literal("hitbox")
				.executes(ctx -> show(ctx.getSource()))
				.then(Commands.literal("set")
					.then(Commands.argument("width", FloatArgumentType.floatArg(HitboxConfig.MIN, HitboxConfig.MAX))
						.then(Commands.argument("height", FloatArgumentType.floatArg(HitboxConfig.MIN, HitboxConfig.MAX))
							.executes(ctx -> apply(ctx.getSource(),
								FloatArgumentType.getFloat(ctx, "width"),
								FloatArgumentType.getFloat(ctx, "height"))))))
				.then(Commands.literal("width")
					.then(Commands.argument("value", FloatArgumentType.floatArg(HitboxConfig.MIN, HitboxConfig.MAX))
						.executes(ctx -> apply(ctx.getSource(),
							FloatArgumentType.getFloat(ctx, "value"), HitboxConfig.height))))
				.then(Commands.literal("height")
					.then(Commands.argument("value", FloatArgumentType.floatArg(HitboxConfig.MIN, HitboxConfig.MAX))
						.executes(ctx -> apply(ctx.getSource(),
							HitboxConfig.width, FloatArgumentType.getFloat(ctx, "value")))))
				.then(Commands.literal("reset")
					.executes(ctx -> apply(ctx.getSource(), 1.0f, 1.0f)))));
	}

	private static int show(CommandSourceStack source) {
		source.sendSuccess(() -> Component.literal(
			"Hitbox multiplier: width x" + HitboxConfig.width + ", height x" + HitboxConfig.height
				+ "  (1.0 = normal; vanilla player is 0.6 wide, 1.8 tall)"), false);
		return 1;
	}

	private static int apply(CommandSourceStack source, float width, float height) {
		HitboxConfig.set(width, height);

		MinecraftServer server = source.getServer();
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			player.refreshDimensions();
		}

		source.sendSuccess(() -> Component.literal(
			"Hitbox set to width x" + HitboxConfig.width + ", height x" + HitboxConfig.height), true);
		return 1;
	}
}
