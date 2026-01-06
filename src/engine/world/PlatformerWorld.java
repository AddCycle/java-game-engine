package engine.world;

import engine.graphics.Renderer;

public class PlatformerWorld implements World2D {
	private TileMap map;
	
	public PlatformerWorld(TileMap tilemap) {
		this.map = tilemap;
	}

	@Override
	public float getWidth()  { return map.getWidth() * map.getTileSize(); }
	
	@Override
    public float getHeight() { return map.getHeight() * map.getTileSize(); }

	@Override
	public void update(float dt) {}

	@Override
	public void render(Renderer renderer, Camera camera) {
		map.render(renderer, 1, camera);
	}

	@Override
	public boolean isColliding(float x, float y, float width, float height) {
		int ts = map.getTileSize();

        int left   = (int)Math.floor(x / ts);
        int right  = (int)Math.floor((x + width - 1) / ts);
        int top    = (int)Math.floor(y / ts);
        int bottom = (int)Math.floor((y + height - 1) / ts);

        for (int ty = top; ty <= bottom; ty++) {
            for (int tx = left; tx <= right; tx++) {
                if (map.isSolid(tx, ty)) return true;
            }
        }
        return false;
	}
}