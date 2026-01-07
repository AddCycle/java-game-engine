package game;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Game;
import engine.entities.EntityManager;
import engine.entities.Player2D;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.inputs.controllers.PlatformerController;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.scene.Scene;
import engine.scene.Scene2D;
import engine.state.play.PlayState;
import engine.world.PlatformerWorld;
import engine.world.TileMap;
import engine.world.TopdownWorld;
import engine.world.World2D;
import engine.world.physics.PlatformerPhysics;
import engine.world.physics.TopdownPhysics;

public class NamelessAdventure implements Game {
	private Engine engine;
	private TileMap tilemap;
	private EntityManager entityManager;
	private PlayerController2D controller;
	private Player2D player;
	private int[] textures;
	private World2D world;
	private Scene2D scene;
	
	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		textures = new int[2];
		textures[0] = engine.getRenderer().loadTexture("resources/grass.png");
		textures[1] = engine.getRenderer().loadTexture("resources/water.png");

		int[][] tiles = {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		};

		int tileSize = 16;
		tilemap = new TileMap(tiles, textures, tileSize);
		world = new TopdownWorld(tilemap);

		if (world instanceof TopdownWorld) {
		    controller = new TopdownController(engine.getInput(), tilemap);
		    entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());
		} else {
		    controller = new PlatformerController(engine.getInput());
		    entityManager = new EntityManager(engine.getCamera(), new PlatformerPhysics());
		}

		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
	    player = new Player2D(controller, playerTex);

	    engine.getCamera().setFollowEntity(player);
	    engine.getCamera().setWorld(world);
	    
	    scene = new Scene(world, engine.getCamera(), player, entityManager);
	    engine.getGameStateManager().set(new PlayState(scene), engine);

	    entityManager.add(player);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();
		
		if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			engine.stop();
		}

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_F11)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}

		// switch between worlds at runtime
		if (input.isKeyJustPressed(GLFW.GLFW_KEY_TAB)) {
		    if (world instanceof TopdownWorld) {
		        World2D platformerWorld = new PlatformerWorld(tilemap);
		        PlayerController2D platformerController = new PlatformerController(input);
		        scene.setWorld(platformerWorld, platformerController);
		        entityManager.setPhysics(new PlatformerPhysics());
		    } else {
		        World2D tilemapWorld = new TopdownWorld(tilemap);
		        PlayerController2D topDownController = new TopdownController(input, tilemap);
		        scene.setWorld(tilemapWorld, topDownController);
		        entityManager.setPhysics(new TopdownPhysics());
		    }
		    world = scene.getWorld();
		}
	}

	@Override
	public void render() {}
}