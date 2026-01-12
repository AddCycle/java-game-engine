package engine.world.map;

import engine.graphics.Renderer;
import engine.world.Camera;

public class TileMapRenderer {

	public void render(TileMap map, Renderer renderer, Camera cam) {
        float camX = cam != null ? cam.x : 0;
        float camY = cam != null ? cam.y : 0;

        for (TileLayer layer : map.getLayers()) {
        		if (layer.isCollisionLayer()) continue;
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    int id = layer.get(x, y);

                    if (id == 0) continue; // Tiled 0 = empty, skip
                    Tile tile = map.getTile(id - 1); // Tiled : shifting all our tile-id by one
                    renderer.drawTexturedRect(
                        tile.textureId,
                        x * map.getTileSize() - camX,
                        y * map.getTileSize() - camY,
                        map.getTileSize(),
                        map.getTileSize()
                    );
                }
            }
        }
    }
}
