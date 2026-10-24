package com.maceed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class HitboxConfig {
	public static final float MIN = 0.1f;
	public static final float MAX = 20.0f;

	public static volatile float width = 1.0f;
	public static volatile float height = 1.0f;
	public static volatile int version = 0;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private HitboxConfig() {}

	private static Path path() {
		return FabricLoader.getInstance().getConfigDir().resolve("maceed.json");
	}

	public static void set(float newWidth, float newHeight) {
		width = clamp(newWidth);
		height = clamp(newHeight);
		version++;
		save();
	}

	private static float clamp(float v) {
		return Math.max(MIN, Math.min(MAX, v));
	}

	public static void load() {
		Path p = path();
		if (!Files.exists(p)) {
			save();
			return;
		}
		try {
			JsonObject o = GSON.fromJson(Files.readString(p), JsonObject.class);
			if (o.has("width")) width = clamp(o.get("width").getAsFloat());
			if (o.has("height")) height = clamp(o.get("height").getAsFloat());
		} catch (Exception e) {
			MaceEDMod.LOGGER.warn("Could not read maceed.json, using defaults", e);
		}
	}

	public static void save() {
		JsonObject o = new JsonObject();
		o.addProperty("width", width);
		o.addProperty("height", height);
		try {
			Files.writeString(path(), GSON.toJson(o));
		} catch (IOException e) {
			MaceEDMod.LOGGER.warn("Could not save maceed.json", e);
		}
	}
}
