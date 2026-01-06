package engine.world;

import engine.graphics.Renderer;

public class TopdownWorld implements World2D {
	private TileMap map;
	
	public TopdownWorld(TileMap map) {
		this.map = map;
	}

	public float getWidth()  { return map.getWidth() * map.getTileSize(); }
    public float getHeight() { return map.getHeight() * map.getTileSize(); }

	@Override
	public void update(float dt) {}

	@Override
	public void render(Renderer renderer, Camera cam) {
		map.render(renderer, 1, cam);
	}

	@Override
	public boolean isColliding(float x, float y, float width, float height) {
		int tileSize = map.getTileSize();

		int left   = (int)Math.floor(x / tileSize);
		int right  = (int)Math.floor((x + width - 1) / tileSize);
		int top    = (int)Math.floor(y / tileSize);
		int bottom = (int)Math.floor((y + height - 1) / tileSize);

        for (int ty = top; ty <= bottom; ty++) {
            for (int tx = left; tx <= right; tx++) {
                if (map.isSolid(tx, ty)) {
                    return true;
                }
            }
        }
        return false;
	}
}