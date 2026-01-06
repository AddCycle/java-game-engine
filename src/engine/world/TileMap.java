package engine.world;

import engine.core.Logger;
import engine.graphics.Renderer;

public class TileMap {
	private int width, height, tileSize;
	private int[][] map;
	private int[] textures;

	/**
	 * Creates a tilemap object which contains the 2d map infos
	 * @param mapData the data of the map (textureID pos inside the array is the id of the tile)
	 * @param tileSize size
	 * @param texturesID
	 */
	public TileMap(int[][] mapData, int[] texturesID, int tileSize) {
		this.width = mapData[0].length;
		this.height = mapData.length;
		this.tileSize = tileSize;
		this.map = mapData;
		this.textures = texturesID;
		Logger.info("Created the tilemap : %d,%d", width, height);
	}
	
	public void update(float dt) {
		
	}
	
	/**
	 * Render the tilemap relative to the camera, if camera == null then absolute
	 * @param renderer
	 * @param scale
	 * @param camera can be null
	 */
	public void render(Renderer renderer, int scale, Camera camera) {
		float camX = 0;
		float camY = 0;
		if (camera != null) {
			camX = camera.x;
			camY = camera.y;
		}

		for (int y = 0; y < map.length; y++) {
		    for (int x = 0; x < map[y].length; x++) {
		        int type = map[y][x];
		        int tex = textures[type];
		        renderer.drawTexturedRect(tex, x * tileSize - camX, y * tileSize - camY, tileSize, tileSize);
		    }
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTileSize() {
		return tileSize;
	}
}