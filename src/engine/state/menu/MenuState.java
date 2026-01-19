package engine.state.menu;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.state.GameState;
import engine.state.menu.core.Menu;

public abstract class MenuState implements GameState {
	protected Menu menu;
	protected Engine engine;
	protected int backgroundTexture = -1;

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
		if (backgroundTexture != -1) {
			renderer.drawTexturedRect(backgroundTexture, 0, 0, engine.getCamera().width, engine.getCamera().height);
		}
		menu.render(renderer);
	}

	@Override
	public void dispose() {
		menu.onClose();
	}
	
	public void setBackgroundTexture(int textureID) {
		backgroundTexture = textureID;
	}
}