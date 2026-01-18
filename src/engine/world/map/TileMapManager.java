package engine.world.map;

import engine.core.Engine;
import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.loader.TiledLoader;
import engine.scene.Scene2D;
import engine.world.TopdownWorld;
import engine.world.World2D;

public class TileMapManager {
	private Engine engine;
    private EntityManager entityManager;
    private TileMap currentMap;
	private Scene2D scene;
	private Tile[] tiles;

    public TileMapManager(Engine engine,
                      EntityManager em,
                      Tile[] tiles) {
        this.engine = engine;
        this.entityManager = em;
        this.tiles = tiles;
    }
    
    public void setScene(Scene2D scene) {
    		this.scene = scene;
    }

    public void loadMap(String mapPath, Tile[] tiles, Entity player, int spawnX, int spawnY) {

        currentMap = TiledLoader.loadFromJSON(mapPath, tiles, entityManager);
        World2D newWorld = new TopdownWorld(this, entityManager);

        scene.setWorld(newWorld);

        player.tileX = spawnX;
        player.tileY = spawnY;
        player.x = spawnX * currentMap.getTileSize();
        player.y = spawnY * currentMap.getTileSize();
    }
    
    public TileMap getCurrentMap() {
        return currentMap;
    }

	public Tile[] getTiles() {
		return tiles;
	}

}
