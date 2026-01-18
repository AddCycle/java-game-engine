package engine.ui.menu;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;
import engine.inputs.keybinds.MenuKeybinds;

public class VerticalMenu implements Menu {

	private MenuItem[] items;
	private int selected = 0;
	private Keybinds keybinds;
	private Engine engine;
	private Inputs inputs;

	public VerticalMenu(Engine engine, MenuItem... items) {
		this.engine = engine;
		this.items = items;
		this.keybinds = new MenuKeybinds();
		this.inputs = engine.getInput();
	}

	@Override
	public void update(float dt) {
		if (keybinds.isJustPressed(inputs, Action.MOVE_UP))
			selected = (selected - 1 + items.length) % items.length;

		if (keybinds.isJustPressed(inputs, Action.MOVE_DOWN))
			selected = (selected + 1) % items.length;

		if (keybinds.isJustPressed(inputs, Action.CONFIRM))
			items[selected].action.run();
	}

	@Override
	public void render(Renderer renderer) {
		float w = engine.getCamera().width;
		float h = engine.getCamera().height;
		for (int i = 0; i < items.length; i++) {
			String prefix = (i == selected) ? "> " : "  ";
			renderer.drawText(prefix + items[i].label, 40, 40 + i * 16);
		}
	}
}