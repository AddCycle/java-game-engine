package engine.world;

import engine.entities.EntityManager;
import engine.graphics.Renderer;
import engine.world.map.TileMap;
import engine.world.map.TileMapManager;
import engine.world.map.TileMapRenderer;

public class PlatformerWorld implements World2D {
	private TileMapManager tilemapMgr;
	private TileMapRenderer tileMapRenderer = new TileMapRenderer();
	private EntityManager entityManager;
	private TileMap map;
	
	public PlatformerWorld(TileMapManager tilemapMgr, EntityManager entityManager) {
		this.tilemapMgr = tilemapMgr;
		this.map = tilemapMgr.getCurrentMap();
		this.entityManager = entityManager;
	}

	@Override
	public float getWidth()  { return map.getWidth() * map.getTileSize(); }
	
	@Override
    public float getHeight() { return map.getHeight() * map.getTileSize(); }

	@Override
	public void update(float dt) {}

	@Override
	public void render(Renderer renderer, Camera cam) {
		tileMapRenderer.render(map, renderer, cam);
	}

	@Override
	public boolean isColliding(float x, float y, float width, float height) {
		int tileSize = map.getTileSize();

		int left = (int) Math.floor(x / tileSize);
		int right = (int) Math.floor((x + width - 1) / tileSize);
		int top = (int) Math.floor(y / tileSize);
		int bottom = (int) Math.floor((y + height - 1) / tileSize);

		for (int ty = top; ty <= bottom; ty++) {
			for (int tx = left; tx <= right; tx++) {
				if (map.isSolid(tx, ty)) {
					return true;
				}
			}
		}
		return false;
	}

//	private void checkConnection(Entity p) {
//		MapConnection c = map.getConnectionAt(p.tileX, p.tileY);
//		if (c == null)
//			return;
//
//		tilemapMgr.loadMap("resources/maps/" + c.targetMap + ".json", tilemapMgr.getTiles(), p, c.entryX, c.entryY);
//	}

	@Override
	public TileMap getTileMap() {
		return map;
	}
}