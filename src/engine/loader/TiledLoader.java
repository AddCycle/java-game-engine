package engine.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import engine.core.Logger;
import engine.world.map.Tile;
import engine.world.map.TileLayer;
import engine.world.map.TileMap;

public class TiledLoader {

	public static TileMap loadFromJSON(String path, Tile[] tiles) {
        Gson gson = new Gson();
        String json = null;

        try {
            json = Files.readString(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject obj = gson.fromJson(json, JsonObject.class);

        int tileSize = obj.get("tilewidth").getAsInt();
        TileMap map = new TileMap(tileSize);
        map.setTiles(tiles);

        JsonArray layers = obj.getAsJsonArray("layers");
        for (JsonElement layerElem : layers) {
            JsonObject layerObj = layerElem.getAsJsonObject();
            
            // Decide if this layer is collision based on its name
            boolean collision = layerObj.get("name").getAsString().toLowerCase().contains("collision");
            Logger.info("collison : " + collision);

            JsonArray data = layerObj.getAsJsonArray("data");
            int width = layerObj.get("width").getAsInt();
            int height = layerObj.get("height").getAsInt();
            int[][] layerTiles = new int[height][width];

            // Convert 1D data array to 2D
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    layerTiles[y][x] = data.get(index).getAsInt();
                }
            }

            TileLayer layer = new TileLayer(layerTiles, collision);
            map.addLayer(layer);
        }

        return map;
    }
}
