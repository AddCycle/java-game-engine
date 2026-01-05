package game;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Game;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.world.Camera;
import engine.world.TileMap;
import inputs.InputTesting;

public class NamelessAdventure implements Game {
	private Engine engine;
	private TileMap tilemap;
	private int[] textures;
	private Camera camera;
	
	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		textures = new int[2];
		textures[0] = engine.getRenderer().loadTexture("resources/grass.png");
		textures[1] = engine.getRenderer().loadTexture("resources/water.png");

		int[][] tiles = {
				{1, 0, 0, 1},
				{0, 1, 1, 0},
				{0, 1, 0, 0},
				{1, 1, 1, 0}
		};
		tilemap = new TileMap(tiles, textures, 16);
		camera = new Camera();
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();
		
//		InputTesting.test1(input);
		
		if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			engine.stop();
		}
		
		if (input.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
			camera.x -= 5 * dt;
		}
		else if (input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
			camera.x += 5 * dt;
		}

		if (input.isKeyDown(GLFW.GLFW_KEY_UP)) {
			camera.y -= 5 * dt;
		}
		else if (input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
			camera.y += 5 * dt;
		}

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_F11)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}
	}

	@Override
	public void render() {
		Renderer renderer = engine.getRenderer();
		int scale = 8;
		tilemap.render(renderer, scale, camera);
	}
}