package game;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Game;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.inputs.Inputs;

public class NamelessAdventure implements Game {
	private Engine engine;
	private int texID;

	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		texID = engine.getRenderer().loadTexture("resources/icon.png");
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();
		
		if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			engine.stop();
		}

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_F11)) {
			Window window = engine.getWindow();
			window.setFullScreen(!window.isFullscreen());
		}
	}

	@Override
	public void render() {
		Renderer renderer = engine.getRenderer();
		renderer.drawTexturedRect(texID);
	}
}