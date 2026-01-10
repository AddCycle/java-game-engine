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
import engine.inputs.keybinds.PlatformerKeybinds;
import engine.inputs.keybinds.TopdownKeybinds;
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

		int[][] tiles = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

		int tileSize = 16;
		tilemap = new TileMap(tiles, textures, tileSize);
//		world = new PlatformerWorld(tilemap);
		world = new TopdownWorld(tilemap);

		boolean tileBasedMovement = true;
		if (world instanceof TopdownWorld) {
			controller = new TopdownController(engine.getInput(), new TopdownKeybinds(), tilemap, tileBasedMovement);
			entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());
		} else if (world instanceof PlatformerWorld) {
			controller = new PlatformerController(engine.getInput(), new PlatformerKeybinds());
			entityManager = new EntityManager(engine.getCamera(), new PlatformerPhysics());
		}

		/** Simple Player2D **/
//		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
//	    player = new Player2D(controller, playerTex);

		/** This part is about AnimatedPlayer2D and some walking animations **/
//		int playerTex = engine.getRenderer().loadTexture("resources/player.png");
		player = new AnimatedPlayer2D(controller);

		int idle_down = engine.getRenderer().loadTexture("resources/idle_down.png");
		int down1 = engine.getRenderer().loadTexture("resources/down1.png");
		int down2 = engine.getRenderer().loadTexture("resources/down2.png");

		int idle_up = engine.getRenderer().loadTexture("resources/idle_up.png");
		int up1 = engine.getRenderer().loadTexture("resources/up1.png");
		int up2 = engine.getRenderer().loadTexture("resources/up2.png");

		int idle_left = engine.getRenderer().loadTexture("resources/idle_left.png");
		int left1 = engine.getRenderer().loadTexture("resources/left1.png");
		int left2 = engine.getRenderer().loadTexture("resources/left2.png");

		int idle_right = engine.getRenderer().loadTexture("resources/idle_right.png");
		int right1 = engine.getRenderer().loadTexture("resources/right1.png");
		int right2 = engine.getRenderer().loadTexture("resources/right2.png");

		Animation idleDown = new Animation(new int[] { idle_down }, 0.2f, true);
		Animation walkDown = new Animation(new int[] { down1, down2 }, 0.4f, true);

		Animation idleUp = new Animation(new int[] { idle_up }, 0.2f, true);
		Animation walkUp = new Animation(new int[] { up1, up2 }, 0.4f, true);

		Animation idleLeft = new Animation(new int[] { idle_left }, 0.2f, true);
		Animation walkLeft = new Animation(new int[] { left1, left2 }, 0.4f, true);

		Animation idleRight = new Animation(new int[] { idle_right }, 0.2f, true);
		Animation walkRight = new Animation(new int[] { right1, right2 }, 0.4f, true);

		player.addAnimation("idle_down", idleDown);
		player.addAnimation("walk_down", walkDown);

		player.addAnimation("idle_up", idleUp);
		player.addAnimation("walk_up", walkUp);

		player.addAnimation("idle_left", idleLeft);
		player.addAnimation("walk_left", walkLeft);

		player.addAnimation("idle_right", idleRight);
		player.addAnimation("walk_right", walkRight);

		player.setStateMachine(e -> {
			AnimatedPlayer2D p = (AnimatedPlayer2D) e;

			String dir = p.facing.getName();

			boolean isMoving = tileBasedMovement ? p.moving : (p.vx != 0 || p.vy != 0);
			Logger.debug("vx, vy: %f, %f", p.vx, p.vy);

			return (isMoving ? "walk_" : "idle_") + dir;
		});

		if (world instanceof TopdownWorld) {
			InteractibleNPC npc = PlayerInteractionTesting.createTestNPC();
			entityManager.add(npc);
		} else if (world instanceof PlatformerWorld) {
			Entity coin = GameObjectsTesting.createTestCoin(player, entityManager);
			entityManager.add(coin);
		}

		float smoothingFactor = 0.1f;
		engine.getCamera().setFollowEntity(player, smoothingFactor);
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
				PlayerController2D platformerController = new PlatformerController(input, new PlatformerKeybinds());
				scene.setWorld(platformerWorld, platformerController);
				entityManager.setPhysics(new PlatformerPhysics());
			} else {
				World2D tilemapWorld = new TopdownWorld(tilemap);
				PlayerController2D topDownController = new TopdownController(input, new TopdownKeybinds(), tilemap);
				scene.setWorld(tilemapWorld, topDownController);
				entityManager.setPhysics(new TopdownPhysics());
			}
			world = scene.getWorld();
		}
	}

	@Override
	public void render() {
	}
}