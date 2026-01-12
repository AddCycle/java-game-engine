package game;

import engine.core.Engine;
import engine.core.Game;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.GeneralKeybinds;
import engine.inputs.keybinds.Keybinds;
import engine.scene.Scene2D;

public class NamelessAdventure implements Game {
	private Engine engine;
	private Keybinds generalKeys;

	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		generalKeys = new GeneralKeybinds();
		
		Scene2D scene = TopdownGameSetup.create(engine);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (generalKeys.isJustPressed(input, Action.FULLSCREEN)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}
	}
}