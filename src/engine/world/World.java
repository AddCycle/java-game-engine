package engine.world;

public class World {
	public float width, height; // in pixels for the camera
	private TileMap map;

	public World(TileMap map) {
		this.map = map;
		this.width = map.getWidth() * map.getTileSize();
		this.height = map.getHeight() * map.getTileSize();
	}
}