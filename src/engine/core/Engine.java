package engine.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.core.Logger.Level;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.imgui.ImGuiLayer;
import engine.inputs.Inputs;
import engine.loader.Loader;
import engine.state.GameState;
import engine.state.GameStateManager;
import engine.ui.UIManager;
import engine.world.Camera;

public class Engine {
	private Window window;
	private Inputs inputs;
	private Loader loader;
	private Renderer renderer;
	private Camera camera;
	private GameStateManager gsm;
	private ImGuiLayer imgui;

	private Game game;

	private boolean vsync;
	private boolean fullscreen;
	private boolean debug;
	private int debugKey = GLFW.GLFW_KEY_0;
	private UIManager uiManager;
	
	public static final int CAMERA_WIDTH = 320;
	public static final int CAMERA_HEIGHT = 180;

	public Engine(Builder builder) {
		this.vsync = builder.vsync;
		this.fullscreen = builder.fullscreen;
		if (builder.debug) {
			Logger.setLevel(Level.DEBUG);
		}

		window = new Window(builder.width, builder.height, builder.title, builder.icon);
		loader = new Loader();
		camera = new Camera(CAMERA_WIDTH, CAMERA_HEIGHT); // TODO: make settings variable(16:9) aspect ratio by default leading to black
		uiManager = new engine.ui.UIManager(this);
										// bars
		renderer = new Renderer(camera, loader);
		gsm = new GameStateManager();
		imgui = new ImGuiLayer();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setState(GameState state) {
		gsm.set(state, this);
	}
	
	private void init() {
		window.create(renderer);

		imgui.init(window.getId());

		loader.getFontLoader().loadDefaultFont();

		if (vsync)
			window.enableVSync();
		if (fullscreen)
			window.setFullScreen(true);

		inputs = new Inputs(window.getId(), imgui.getImGuiGlfw());

		game.init();
	}

	public void run() {
		init();

		double lastTime = GLFW.glfwGetTime();

		while (!window.shouldClose()) {
			double currentTime = GLFW.glfwGetTime();
			float dt = (float) (currentTime - lastTime);
			lastTime = currentTime;

			int fps = (int)(1.0f / dt);

			imgui.begin();
			inputs.update();
			window.pollEvents();
			
			if (inputs.isKeyJustPressed(debugKey)) {
				debug = !debug;
			}

			camera.update(dt);

			gsm.update(dt);
			game.update(dt);

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			gsm.render(renderer);

			if (debug) game.debug(fps);
			imgui.end();

			window.swapBuffers();
		}

		cleanUp();
	}

	private void cleanUp() {
		imgui.dispose();
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

		public Builder width(int w) {
			this.width = w;
			return this;
		}

		public Builder height(int h) {
			this.height = h;
			return this;
		}

		public Builder title(String t) {
			this.title = t;
			return this;
		}

		public Builder icon(String i) {
			this.icon = i;
			return this;
		}

		public Builder vsync(boolean v) {
			this.vsync = v;
			return this;
		}

		public Builder fullscreen(boolean f) {
			this.fullscreen = f;
			return this;
		}

		public Builder debug() {
			this.debug = true;
			return this;
		}

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

	public GameStateManager getGameStateManager() {
		return gsm;
	}

	public UIManager getUIManager() {
		return uiManager;
	}

	public ImGuiLayer getImGui() {
		return imgui;
	}

	public int getDebugKey() {
		return debugKey;
	}

	public void setDebugKey(int debugKey) {
		this.debugKey = debugKey;
	}
}