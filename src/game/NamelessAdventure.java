package game;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Game;
import engine.entities.EntityManager;
import engine.entities.Player2D;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.world.Camera;
import engine.world.TileMap;

public class NamelessAdventure implements Game {
	private Engine engine;
	private TileMap tilemap;
	private EntityManager entityManager;
	private Player2D player;
	private int[] textures;
	
	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		entityManager = new EntityManager(engine.getCamera());

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

		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
	    player = new Player2D(engine.getInput(), 32, 32, playerTex);

	    engine.getCamera().setFollowEntity(player);

	    entityManager.add(player);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();
		
//		InputTesting.test1(input);
		
		if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			engine.stop();
		}

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_F11)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}
		
		entityManager.update(dt);
	}

	@Override
	public void render() {
		Renderer renderer = engine.getRenderer();
		int scale = 8;
		tilemap.render(renderer, scale, engine.getCamera());
		entityManager.render(engine.getRenderer());
	}
}