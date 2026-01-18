package engine.loader;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import engine.core.Logger;
import engine.entities.EntityManager;
import engine.world.map.MapConnection;
import engine.world.map.Tile;
import engine.world.map.TileLayer;
import engine.world.map.TileMap;
import entities.interaction.InteractibleNPC;
import entities.interaction.PlayerInteractionTesting;

public class TiledLoader {

	public static TileMap loadFromJSON(String path, Tile[] tiles, EntityManager entityManager) {
		Gson gson = new Gson();
		String json = null;

		try {
			json = Files.readString(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonObject root = gson.fromJson(json, JsonObject.class);

		int tileSize = root.get("tilewidth").getAsInt();
		TileMap map = new TileMap(tileSize);
		map.setTiles(tiles);

		JsonArray layers = root.getAsJsonArray("layers");

		for (JsonElement layerElem : layers) {
			JsonObject layerObj = layerElem.getAsJsonObject();
			String type = layerObj.get("type").getAsString();
			String name = layerObj.get("name").getAsString();

			/* ---------------- TILE LAYERS ---------------- */
			if (type.equals("tilelayer")) {

				boolean collision = name.toLowerCase().contains("collision");

				JsonArray data = layerObj.getAsJsonArray("data");
				int width = layerObj.get("width").getAsInt();
				int height = layerObj.get("height").getAsInt();
				int[][] layerTiles = new int[height][width];

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int index = y * width + x;
						layerTiles[y][x] = data.get(index).getAsInt();
					}
				}

				map.addLayer(new TileLayer(layerTiles, collision));
			}

			/* ---------------- CONNECTIONS ---------------- */
			if (type.equals("objectgroup") && name.equals("connections")) {

			    JsonArray objects = layerObj.getAsJsonArray("objects");

			    for (JsonElement objElem : objects) {
			        JsonObject obj = objElem.getAsJsonObject();

			        MapConnection c = new MapConnection();

			        c.fromX = obj.get("x").getAsInt() / tileSize;
			        c.fromY = obj.get("y").getAsInt() / tileSize;

			        JsonArray props = obj.getAsJsonArray("properties");

			        c.targetMap = getProperty(props, "targetMap").getAsString();
			        c.entryX = getProperty(props, "entryX").getAsInt();
			        c.entryY = getProperty(props, "entryY").getAsInt();

			        map.addConnection(c);
			    }

			}

			/* ---------------- NPCs ---------------- */
			if (type.equals("objectgroup") && name.equalsIgnoreCase("npcs")) {

			    JsonArray objects = layerObj.getAsJsonArray("objects");

			    for (JsonElement objElem : objects) {
			        JsonObject obj = objElem.getAsJsonObject();

			        int x = obj.get("x").getAsInt() / tileSize;
			        int y = obj.get("y").getAsInt() / tileSize;
			        Logger.info("npc : %d, %d", x, y);

			        Random rand = new Random();
			        InteractibleNPC npc = PlayerInteractionTesting.createTestNPC(x * tileSize, y * tileSize, 16, 16, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

			        JsonArray props = obj.getAsJsonArray("properties");

			        npc.dialog = getProperty(props, "dialog").getAsString();

			        map.addEntity(npc);
			    }

			}
		}
		
		map.getConnections().forEach(c -> {
			Logger.info("connection to : %s, x,y:%d,%d", c.targetMap, c.entryX, c.entryY);
		});

		return map;
	}

	private static JsonElement getProperty(JsonArray props, String name) {
		for (JsonElement e : props) {
			JsonObject p = e.getAsJsonObject();
			if (p.get("name").getAsString().equals(name)) {
				return p.get("value");
			}
		}
		return null;
	}
}
