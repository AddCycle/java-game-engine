package game;

import org.lwjgl.glfw.GLFW;

import engine.animations.Animation;
import engine.core.Engine;
import engine.core.Game;
import engine.core.Logger;
import engine.entities.AnimatedPlayer2D;
import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.inputs.controllers.PlatformerController;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.scene.AnimatedPlayerScene;
import engine.scene.Scene2D;
import engine.state.menu.PauseState;
import engine.state.play.PlayState;
import engine.world.PlatformerWorld;
import engine.world.TileMap;
import engine.world.TopdownWorld;
import engine.world.World2D;
import engine.world.physics.PlatformerPhysics;
import engine.world.physics.TopdownPhysics;
import entities.interaction.InteractibleNPC;
import entities.interaction.PlayerInteractionTesting;
import entities.objects.GameObjectsTesting;

public class NamelessAdventure implements Game {
	private Engine engine;
	private TileMap tilemap;
	private EntityManager entityManager;
	private PlayerController2D controller;
//	private Player2D player;
	private AnimatedPlayer2D player;
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
//		world = new PlatformerWorld(tilemap);
		world = new TopdownWorld(tilemap);

		if (world instanceof TopdownWorld) {
		    controller = new TopdownController(engine.getInput(), tilemap);
		    entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());
		} else if (world instanceof PlatformerWorld) {
		    controller = new PlatformerController(engine.getInput());
		    entityManager = new EntityManager(engine.getCamera(), new PlatformerPhysics());
		}

//		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
//	    player = new Player2D(controller, playerTex);

		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
	    player = new AnimatedPlayer2D(controller);
	    
	    int tex0 = engine.getRenderer().loadTexture("resources/idle_down.png");
	    int tex1 = engine.getRenderer().loadTexture("resources/down1.png");
	    int tex2 = engine.getRenderer().loadTexture("resources/down2.png");

		Animation idleDown = new Animation(new int[]{tex0}, 0.2f, true);
		Animation walkDown = new Animation(new int[]{tex1, tex2}, 0.2f, true);
	    
	    player.addAnimation("idle_down", idleDown);
	    player.addAnimation("walk_down", walkDown);

	    player.setStateMachine(e -> {
	        AnimatedPlayer2D p = (AnimatedPlayer2D) e;

	        if (!p.moving) {
	        		Logger.debug("not moving the player");
	            return "idle_" + p.facing.getName();
	        }
	        	Logger.debug("MOVING");
	        return "walk_" + p.facing.getName();
	    });
	    
	    if (world instanceof TopdownWorld) {
	    		InteractibleNPC npc = PlayerInteractionTesting.createTestNPC();
	    		entityManager.add(npc);
		} else if (world instanceof PlatformerWorld) {
			Entity coin = GameObjectsTesting.createTestCoin(player, entityManager);
			entityManager.add(coin);
	    }

	    engine.getCamera().setFollowEntity(player);
	    engine.getCamera().setWorld(world);
	    
	    scene = new AnimatedPlayerScene(world, engine.getCamera(), player, entityManager);
	    engine.getGameStateManager().set(new PlayState(scene), engine);

	    entityManager.add(player);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();
		
		if (input.isKeyJustPressed(GLFW.GLFW_KEY_ESCAPE)) {
			Logger.debug("switched to gamestate pause");
			engine.getGameStateManager().push(new PauseState(), engine);
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