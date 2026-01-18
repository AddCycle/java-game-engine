package engine.world.map;

import engine.entities.Entity;

public class MapConnection {
	public Integer fromX, fromY;
    public String targetMap;
    public int entryX, entryY;
	
	public MapConnection() {
		
	}

	public static void checkConnection(Entity p, TileMapManager tilemapMgr) {
		MapConnection c = tilemapMgr.getCurrentMap().getConnectionAt(p.tileX, p.tileY);
		if (c == null)
			return;

		tilemapMgr.setCurrentMap(c.targetMap, p);

		int spawnX = c.entryX;
		int spawnY = c.entryY;
		p.tileX = spawnX;
		p.tileY = spawnY;
		p.x = spawnX * tilemapMgr.getCurrentMap().getTileSize();
		p.y = spawnY * tilemapMgr.getCurrentMap().getTileSize();
	}

}