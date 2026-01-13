package engine.state.menu;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.state.GameState;
import engine.ui.menu.Menu;

public abstract class MenuState implements GameState {
	protected Menu menu;
	protected Engine engine;

	@Override
	public void init(Engine engine) {
		this.engine = engine;
        menu = createMenu();
        menu.onOpen();
	}
	
	protected abstract Menu createMenu();

	@Override
	public void update(float dt) {
		menu.update(dt);
	}

	@Override
	public void render(Renderer renderer) {
		menu.render(renderer);
	}

	@Override
	public void dispose() {
		menu.onClose();
	}
}