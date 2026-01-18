package engine.world;

import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.entities.movement.MovementMode;
import engine.graphics.Renderer;
import engine.world.map.MapConnection;
import engine.world.map.TileMap;
import engine.world.map.TileMapManager;
import engine.world.map.TileMapRenderer;

public class TopdownWorld implements World2D {
	private TileMapManager tilemapMgr;
	private TileMapRenderer tileMapRenderer = new TileMapRenderer();
	private EntityManager entityManager;
	private TileMap map;

	public TopdownWorld(TileMapManager tilemapMgr, EntityManager entityManager) {
		this.tilemapMgr = tilemapMgr;
		this.map = tilemapMgr.getCurrentMap();
		this.entityManager = entityManager;
	}

	public float getWidth() {
		return map.getWidth() * map.getTileSize();
	}

	public float getHeight() {
		return map.getHeight() * map.getTileSize();
	}

	@Override
	public void update(float dt) {
		for (Entity e : entityManager.getEntities()) {
			if (e.getMovementMode() == MovementMode.TILE) {
	            if (e.moving) moveToTarget(e, dt);
	            else tryStartMove(e);
	        } else {
	            tryMoveFreely(e, dt);
	        }
		}
	}

	private void tryMoveFreely(Entity p, float dt) {
		float dx = p.intentDX;
	    float dy = p.intentDY;

	    if (dx != 0 && dy != 0) {
	        float inv = (float)(1 / Math.sqrt(2));
	        dx *= inv;
	        dy *= inv;
	    }

	    float speed = p.getSpeed();

	    p.vx = dx * speed;
	    p.vy = dy * speed;

	    float newX = p.x + p.vx * dt;
	    float newY = p.y + p.vy * dt;

	    if (!isColliding(newX, p.y, p.width, p.height)) p.x = newX;
	    if (!isColliding(p.x, newY, p.width, p.height)) p.y = newY;
	    
	    int oldTileX = p.tileX;
	    int oldTileY = p.tileY;

	    p.tileX = (int)(p.x / map.getTileSize());
	    p.tileY = (int)(p.y / map.getTileSize());

	    if (p.tileX != oldTileX || p.tileY != oldTileY) {
			MapConnection.checkConnection(p, tilemapMgr);
	    }
	}

	@Override
	public void render(Renderer renderer, Camera cam) {
		tileMapRenderer.render(tilemapMgr.getTileset(), map, renderer, cam);
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

	private void tryStartMove(Entity p) {
		if (p.intentDX == 0 && p.intentDY == 0)
			return;

		int tx = p.tileX + p.intentDX;
		int ty = p.tileY + p.intentDY;

		if (map.isSolid(tx, ty))
			return;
		if (entityManager.isEntityBlockingTile(tx, ty, p, map))
			return;

		p.targetTileX = tx;
		p.targetTileY = ty;
		p.moving = true;
	}

	private void moveToTarget(Entity p, float dt) {
		float targetX = p.targetTileX * map.getTileSize();
		float targetY = p.targetTileY * map.getTileSize();

		float speed = p.getSpeed();

		p.x = approach(p.x, targetX, speed * dt);
		p.y = approach(p.y, targetY, speed * dt);

		if (p.x == targetX && p.y == targetY) {
			p.tileX = p.targetTileX;
			p.tileY = p.targetTileY;
			p.moving = false;

			MapConnection.checkConnection(p, tilemapMgr);
		}
	}

	private float approach(float current, float target, float maxDelta) {
		if (current < target)
			return Math.min(current + maxDelta, target);
		return Math.max(current - maxDelta, target);
	}

	@Override
	public TileMap getTileMap() {
		return map;
	}

	@Override
	public void setTileMap(TileMap map) {
		this.map = map;
	}
}