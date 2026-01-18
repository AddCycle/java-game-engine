package engine.world.map;

import java.util.HashMap;
import java.util.Map;

import engine.core.Engine;
import engine.core.Logger;
import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.graphics.Tileset;
import engine.loader.TiledLoader;
import engine.scene.Scene2D;

public class TileMapManager {
	private Engine engine;
	private EntityManager entityManager;
	private TileMap currentMap;
	private Tileset tileset; // TODO make a tileset array/hashmap in order to use multiple tilesets for different maps
	private Scene2D scene;
	private Map<String, TileMap> maps = new HashMap<>();

	public TileMapManager(Engine engine, EntityManager em, Tileset tileset) {
		this.engine = engine;
		this.entityManager = em;
		this.tileset = tileset;
	}

	public void setScene(Scene2D scene) {
		this.scene = scene;
	}

	/**
	 * Preloads the map but doesn't set it to the current map
	 * 
	 * @param mapName
	 * @param mapPath
	 */
	public void addMap(String mapName, String mapPath) {
		TileMap map = TiledLoader.loadFromJSON(mapPath, entityManager);
		if (map == null) {
			Logger.error("TileMapManager tried to load unexistent map : %s", mapName);
			return;
		}
		maps.put(mapName, map);
	}

	// TODO : simplify this method, load the spawnX/Y inside the map properties to
	// reset player spawn
	public void setCurrentMap(String mapName, Entity player) {
		if (!maps.containsKey(mapName)) {
			Logger.error("TileMapManager tried to set unloaded map : %s", mapName);
			return;
		}

		currentMap = maps.get(mapName);

		scene.getWorld().setTileMap(currentMap);

		Logger.debug("TileMapManager successfully set map : %s to current", mapName);
		
		// entities setup
		entityManager.clearEntitiesSafely();
		entityManager.addAllSafely(currentMap.getEntities());

		int spawnX = 0;
		int spawnY = 0;
        player.tileX = spawnX;
        player.tileY = spawnY;
        player.x = spawnX * currentMap.getTileSize();
        player.y = spawnY * currentMap.getTileSize();
	}

	public TileMap getCurrentMap() {
		return currentMap;
	}

	public Tileset getTileset() {
		return tileset;
	}
}