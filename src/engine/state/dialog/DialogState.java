package engine.state.dialog;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.state.GameState;

public class DialogState implements GameState {
	private Engine engine;
	private String text;
	
	public DialogState(String text) {
		this.text = text;
	}

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		// FIXME : replace with keybinds
		if (engine.getInput().isKeyJustPressed(GLFW.GLFW_KEY_ENTER) ||
			engine.getInput().isPadButtonJustPressed(GLFW.GLFW_GAMEPAD_BUTTON_B)) {
            engine.getGameStateManager().pop();
        }
	}

	@Override
	public void render(Renderer renderer) {
		float w = 260;
        float h = 50;
        float x = 30;
        float y = 120;

        renderer.drawDialogBox(x, y, w, h);
        renderer.drawText(text, x + 8, y + 16, 8, 8);
	}

	@Override
	public void dispose() {
		
	}
}
