package game;

import engine.core.Engine;

public class TexturePresets {

	public static int[] basicTiles(Engine engine) {
		int[] textures = new int[3];

		textures[0] = engine.getRenderer().loadTexture("resources/grass.png");
		textures[1] = engine.getRenderer().loadTexture("resources/water.png");
		textures[2] = engine.getRenderer().loadTexture("resources/collision.png");
		
		return textures;
	}
}
