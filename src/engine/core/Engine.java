package engine.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.core.Logger.Level;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.inputs.Inputs;
import engine.loader.Loader;
import engine.world.Camera;

public class Engine {
	private Window window;
	private Inputs inputs;
	private Loader loader;
	private Renderer renderer;
	private Camera camera;

	private Game game;
	
    private boolean vsync;
    private boolean fullscreen;

	public Engine(Builder builder) {
		this.vsync = builder.vsync;
        this.fullscreen = builder.fullscreen;
        if (builder.debug) Logger.setLevel(Level.DEBUG);

		window = new Window(builder.width, builder.height, builder.title, builder.icon);
		loader = new Loader();
		camera = new Camera(320, 180); // TODO: make settings variable(16:9) aspect ratio by default leading to black bars
		renderer = new Renderer(camera, loader.getTextureLoader());
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void run() {
		window.create(renderer);
		
		if (vsync) window.enableVSync();
		if (fullscreen) window.setFullScreen(true);

		inputs = new Inputs(window.getId());

		game.init();
		
		double lastTime = GLFW.glfwGetTime();
		
		while (!window.shouldClose()) {
			double currentTime = GLFW.glfwGetTime();
		    float dt = (float)(currentTime - lastTime);
		    lastTime = currentTime;

		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            inputs.update();
            window.pollEvents();

            camera.update(dt);
            game.update(dt);

            game.render();
            window.swapBuffers();
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
		private String title = "Made with AddCycle's Engine";
		private String icon;
		private boolean vsync = false;
		private boolean fullscreen = false;
		private boolean debug = false;
		
		public Builder() {
		}
		
		public Builder width(int w) { this.width = w; return this; }
        public Builder height(int h) { this.height = h; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder icon(String i) { this.icon = i; return this; }
        public Builder vsync(boolean v) { this.vsync = v; return this; }
        public Builder fullscreen(boolean f) { this.fullscreen = f; return this; }
        public Builder debug() { this.debug = true; return this; }

        public Engine build() {
            return new Engine(this);
        }
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

	public Camera getCamera() {
		return camera;
	}
}
