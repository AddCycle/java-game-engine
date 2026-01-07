package engine.state.menu;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Logger;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.state.GameState;

public class PauseState implements GameState {
	private Engine engine;

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_ENTER)) {
			Logger.debug("poped gamestate pause");
			engine.getGameStateManager().pop();
		}

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_ESCAPE)) {
			Logger.debug("poped gamestate pause");
			engine.getGameStateManager().pop();
			engine.stop();
		}
	}

	@Override
	public void render(Renderer renderer) {
		float w = engine.getWindow().width;
		float h = engine.getWindow().height;
		Renderer.drawRect(0, 0, w, h);
	}

	@Override
	public void dispose() {}

}
