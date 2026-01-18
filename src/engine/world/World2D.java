package engine.world;

import engine.graphics.Renderer;
import engine.world.map.TileMap;

public interface World2D {
	public TileMap getTileMap();
	public void setTileMap(TileMap map);
	float getWidth();
	float getHeight();
	void update(float dt);
	void render(Renderer renderer, Camera camera);
	public boolean isColliding(float x, float y, float width, float height);
}
