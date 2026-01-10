package engine.state.dialog;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;
import engine.state.GameState;

public class DialogState implements GameState {
	private Engine engine;
	private String text;
	private Keybinds keybinds;
	
	public DialogState(String text, Keybinds keybinds) {
		this.text = text;
		this.keybinds = keybinds;
	}

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		if (keybinds.isJustPressed(engine.getInput(), Action.CONFIRM)) {
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
	public void dispose() {}
}