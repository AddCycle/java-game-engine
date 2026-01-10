package engine.state.menu;

import engine.core.Engine;
import engine.core.Logger;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;
import engine.state.GameState;

public class PauseState implements GameState {
	private Engine engine;
	private Keybinds keybinds;
	
	public PauseState(Keybinds keybinds) {
		this.keybinds = keybinds;
	}

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (keybinds.isJustPressed(input, Action.RESUME)) {
			Logger.debug("poped gamestate pause");
			engine.getGameStateManager().pop();
		}

		if (keybinds.isJustPressed(input, Action.QUIT)) {
			Logger.debug("poped gamestate pause");
			engine.getGameStateManager().pop();
			engine.stop();
		}
	}

	@Override
	public void render(Renderer renderer) {
		float w = engine.getCamera().width;
		float h = engine.getCamera().height;
		renderer.drawTextCenteredInBounds("GAME PAUSED", w, h);
	}

	@Override
	public void dispose() {}

	@Override
	public boolean blocksRenderBelow() {
		return false;
	}
}