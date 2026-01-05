package engine.core;

import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.loader.Loader;
import engine.loader.TextureLoader;

public class Engine {
	private Window window;
	private Inputs inputs;
	private Loader loader;
	private Renderer renderer;

	private Game game;
	
    private boolean vsync;
    private boolean fullscreen;
    public static boolean debug;

	public Engine(Builder builder) {
		this.vsync = builder.vsync;
        this.fullscreen = builder.fullscreen;
        this.debug = builder.debug;

		window = new Window(builder.width, builder.height, builder.title);
		loader = new Loader();
		renderer = new Renderer(loader.getTextureLoader());
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void run() {
		window.create();
		window.setIcon("resources/icon.png");
		
		if (vsync) window.enableVSync();
		if (fullscreen) window.setFullScreen(true);

		inputs = new Inputs(window.getId());

		game.init();
		
		while (!window.shouldClose()) {
			float dt = 0.016f;
            game.update(dt);
            game.render();
            window.swapBuffers();
            window.pollEvents();
		}
		
		cleanUp();
	}
	
	private void cleanUp() {
		window.destroy();
		loader.destroy();
	}
	
	@SuppressWarnings("unused")
	public static class Builder {
		
		// window & rendering settings
		private int width = 800, height = 600;
		private String title = "Made with Engine";
		private boolean vsync = false;
		private boolean fullscreen = false;
		private boolean debug = false;
		
		public Builder() {
		}
		
		public Builder width(int w) { this.width = w; return this; }
        public Builder height(int h) { this.height = h; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder vsync(boolean v) { this.vsync = v; return this; }
        public Builder fullscreen(boolean f) { this.fullscreen = f; return this; }
        public Builder debug() { this.debug = true; return this; }

        public Engine build() {
            return new Engine(this);
        }
	}

	public static boolean debug() {
		return debug;
	}

	public Inputs getInput() {
		return inputs;
	}

	public Loader getLoader() {
		return loader;
	}
	
	public void stop() {
		window.close();
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Window getWindow() {
		return window;
	}
}
