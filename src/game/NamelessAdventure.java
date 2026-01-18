package game;

import engine.core.Engine;
import engine.core.Game;
import engine.entities.EntityManager;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.controllers.PlatformerController;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.inputs.keybinds.GeneralKeybinds;
import engine.inputs.keybinds.Keybinds;
import engine.inputs.keybinds.PlatformerKeybinds;
import engine.inputs.keybinds.PlayKeybinds;
import engine.inputs.keybinds.TopdownKeybinds;
import engine.scene.AnimatedPlayerScene;
import engine.state.menu.MainMenuState;
import engine.state.play.PlayState;
import engine.world.PlatformerWorld;
import engine.world.TopdownWorld;
import engine.world.map.Tile;
import engine.world.map.TileMapManager;
import engine.world.physics.PlatformerPhysics;
import engine.world.physics.TopdownPhysics;
import imgui.ImGui;
import imgui.type.ImBoolean;

public class NamelessAdventure implements Game {
	private Engine engine;
	private Keybinds generalKeys;
	private TileMapManager tilemapMgr;
	private int[] textures;
	private Tile[] tuiles;
	private AnimatedPlayerScene scene;
	private PlayState playState;
	private EntityManager entityManager;

	// DEBUG VARIABLES
	private final float[] parallaxSpeed = new float[] {0.5f};
	private final ImBoolean showGrid = new ImBoolean(true);
	// TODO : get rid of it when finished
	private final ImBoolean usingTopdownPhysics = new ImBoolean(true); // only to test platformer physics too

	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		generalKeys = new GeneralKeybinds();

		entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());
		
		textures = TexturePresets.basicTiles(engine);
		tuiles = TilePresets.basicTiles(engine, textures);

		tilemapMgr = new TileMapManager(engine, entityManager, tuiles);
		scene = TopdownGameSetup.create(engine, entityManager);

		tilemapMgr.setScene(scene);

		tilemapMgr.loadMap("resources/maps/sample1.json", tuiles, scene.getPlayer(), 0, 0);

		scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));

		playState = new PlayState(scene, new PlayKeybinds());
		engine.setState(new MainMenuState(playState));
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (generalKeys.isJustPressed(input, Action.FULLSCREEN)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}

		if (generalKeys.isJustPressed(input, Action.TAB)) {
			tilemapMgr.loadMap("resources/maps/sample2.json", tuiles, scene.getPlayer(), 0, 0);
			scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));
		}

//		if (generalKeys.isJustPressed(input, Action.TAB)) {
//			tilemapMgr.loadMap("resources/maps/sample2.json", tuiles, scene.getPlayer(), 0, 0);
//			scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));
//		}
	}
	
	@Override
	public void debug() {
		drawDebugUI();
		
		// TODO : complete so the player can indeed change controller and other things relative to a platformer world
		if ((entityManager.getPhysics() instanceof TopdownPhysics) && !usingTopdownPhysics.get()) {
			PlayerController2D controller = new PlatformerController(engine.getInput(), new PlatformerKeybinds());
			scene.getPlayer().setController(controller);
			entityManager.setPhysics(new PlatformerPhysics());
			scene.setWorld(new PlatformerWorld(tilemapMgr.getCurrentMap()));
		} else if ((entityManager.getPhysics() instanceof PlatformerPhysics) && usingTopdownPhysics.get()) {
			PlayerController2D controller = new TopdownController(engine.getInput(), new TopdownKeybinds());
			scene.getPlayer().setController(controller);
			entityManager.setPhysics(new TopdownPhysics());
			scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));
		}
	}

	private void drawDebugUI() {
		ImGui.begin("Debug");

		ImGui.sliderFloat("Parallax speed", parallaxSpeed, 0.0f, 5.0f);
		ImGui.checkbox("Show grid", showGrid);
		ImGui.checkbox("Using ? topdown physics (true) : platformers one (false)", usingTopdownPhysics);

		ImGui.end();
	}
}