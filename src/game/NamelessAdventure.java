package game;

import engine.core.Engine;
import engine.core.Game;
import engine.entities.EntityManager;
import engine.graphics.Tileset;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.controllers.PlatformerController;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.inputs.keybinds.DialogKeybinds;
import engine.inputs.keybinds.GeneralKeybinds;
import engine.inputs.keybinds.Keybinds;
import engine.inputs.keybinds.PlatformerKeybinds;
import engine.inputs.keybinds.PlayKeybinds;
import engine.inputs.keybinds.TopdownKeybinds;
import engine.scene.AnimatedPlayerScene;
import engine.state.dialog.DialogState;
import engine.state.menu.MainMenuState;
import engine.state.menu.presets.PauseState;
import engine.state.play.PlayState;
import engine.world.PlatformerWorld;
import engine.world.TopdownWorld;
import engine.world.map.TileMapManager;
import engine.world.physics.PlatformerPhysics;
import engine.world.physics.TopdownPhysics;
import imgui.ImGui;
import imgui.type.ImBoolean;

/**
 * TODO : write proper test games & classes in order to make a covering testsuite
 * to manage things and not worry on them being broken or not...
 */
public class NamelessAdventure implements Game {
	private Engine engine;
	private Keybinds generalKeys;
	private TileMapManager tilemapMgr;
	private Tileset tileset;
	private AnimatedPlayerScene scene;
	private EntityManager entityManager;
	
	// GameStates
	private MainMenuState mainMenuState;
	private PlayState playState;
	private PauseState pauseState;

	// DEBUG VARIABLES
	private final float[] parallaxSpeed = new float[] { 0.5f };
	private final ImBoolean showGrid = new ImBoolean(true);
	// TODO : get rid of it when finished
	private final ImBoolean usingTopdownPhysics = new ImBoolean(true); // only to test platformer physics too
	
	private int backgroundTexture = -1; // background menu texture

	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		generalKeys = new GeneralKeybinds();

		entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());

		int tileSize = 16;
		tileset = new Tileset("resources/tilesets/basic_tileset.png", tileSize);
		backgroundTexture = engine.getLoader().getTextureLoader().loadTexture("resources/backgrounds/main_menu.png");

		tilemapMgr = new TileMapManager(engine, entityManager, tileset);
		scene = TopdownGameSetup.create(engine, entityManager);

		tilemapMgr.setScene(scene);

		// map init
		tilemapMgr.addMap("sample1", "resources/maps/sample1.json");
		tilemapMgr.addMap("sample2", "resources/maps/sample2.json");

		scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));

		tilemapMgr.setCurrentMap("sample1", scene.getPlayer());

		// gamestates init
		playState = new PlayState(scene, new PlayKeybinds());
		mainMenuState = new MainMenuState(playState);
		mainMenuState.setBackgroundTexture(backgroundTexture);
		pauseState = new PauseState(mainMenuState, playState);
		
		playState.setPauseState(pauseState);

		engine.setState(mainMenuState);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (generalKeys.isJustPressed(input, Action.FULLSCREEN)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}

		if (generalKeys.isJustPressed(input, Action.TAB)) {
//			tilemapMgr.setCurrentMap("sample1", scene.getPlayer());
			engine.getGameStateManager().push(new DialogState("You are now stuck here forever... (Just kidding)", new DialogKeybinds()), engine);
		}
	}

	@Override
	public void debug(int fps) {
		drawDebugUI(fps);

		handleWorldSwitchDebug();
	}

	private void drawDebugUI(int fps) {
		ImGui.begin("Debug");

		ImGui.text("FPS: " + fps);
		ImGui.checkbox("topdown (check) or platformer (uncheck)", usingTopdownPhysics);
		ImGui.sliderFloat("Parallax speed (not working here)", parallaxSpeed, 0.0f, 5.0f);
		ImGui.checkbox("Show grid (not working)", showGrid);

		ImGui.end();
	}

	private void handleWorldSwitchDebug() {
		if ((entityManager.getPhysics() instanceof TopdownPhysics) && !usingTopdownPhysics.get()) {
			PlayerController2D controller = new PlatformerController(engine.getInput(), new PlatformerKeybinds());
			scene.getPlayer().setController(controller);
			entityManager.setPhysics(new PlatformerPhysics());
			scene.setWorld(new PlatformerWorld(tilemapMgr, entityManager));
		} else if ((entityManager.getPhysics() instanceof PlatformerPhysics) && usingTopdownPhysics.get()) {
			PlayerController2D controller = new TopdownController(engine.getInput(), new TopdownKeybinds());
			scene.getPlayer().setController(controller);
			entityManager.setPhysics(new TopdownPhysics());
			scene.setWorld(new TopdownWorld(tilemapMgr, entityManager));
		}
	}
}