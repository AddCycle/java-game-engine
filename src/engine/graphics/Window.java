package engine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import engine.core.Logger;
import engine.world.Camera;

public class Window {
	private long window;
	public int width;
	public int height;
	private String title;
	private boolean fullscreen;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public boolean create(Camera camera) {
		if (!GLFW.glfwInit()) {
			Logger.error("Failed to create window !");
			return false;
		}

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		centerWindow();
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Renderer.updateViewport(width, height);
		float worldW = width / Renderer.ZOOM;
		float worldH = height / Renderer.ZOOM;
		camera.width = worldW;
		camera.height = worldH;
		GLFW.glfwShowWindow(window);

		// resize callback
		GLFW.glfwSetFramebufferSizeCallback(window, (w, width, height) -> {
		    Renderer.updateViewport(width, height);

		    float worldWbis = width / Renderer.ZOOM;
		    float worldHbis = height / Renderer.ZOOM;
		    camera.width = worldWbis;
		    camera.height = worldHbis;
		});

		Logger.info("Window created successfully");
		
		return true;
	}
	
	public void setIcon(String path) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
		    IntBuffer w = stack.mallocInt(1);
		    IntBuffer h = stack.mallocInt(1);
		    IntBuffer comp = stack.mallocInt(1);

		    ByteBuffer pixels = STBImage.stbi_load(path, w, h, comp, 4);
		    if (pixels == null) {
		        System.out.println("Failed to load icon: " + STBImage.stbi_failure_reason());
		    } else {
		        GLFWImage.Buffer icon = GLFWImage.malloc(1, stack);
		        icon.width(w.get(0));
		        icon.height(h.get(0));
		        icon.pixels(pixels);
		        GLFW.glfwSetWindowIcon(window, icon);
		        STBImage.stbi_image_free(pixels);
		    }
		}
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}

	public void enableVSync() {
		GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_FALSE);
	}

	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}

	public void pollEvents() {
		GLFW.glfwPollEvents();
	}
	
	public void centerWindow() {
		long monitor = GLFW.glfwGetPrimaryMonitor();
	    GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);

	    if (vidMode != null) {
	        int windowWidth = this.width;
	        int windowHeight = this.height;

	        int x = (vidMode.width() - windowWidth) / 2;
	        int y = (vidMode.height() - windowHeight) / 2;

	        GLFW.glfwSetWindowPos(window, x, y);
	    }
	}

	public void setFullScreen(boolean b) {
		long monitor = GLFW.glfwGetPrimaryMonitor();
		GLFWVidMode mode = GLFW.glfwGetVideoMode(monitor);
		if (b) {
			GLFW.glfwSetWindowMonitor(window, monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
			Renderer.updateViewport(mode.width(), mode.height());
			fullscreen = true;
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, 0, 0, width, height, mode.refreshRate());
			Renderer.updateViewport(width, height);
			centerWindow();
			fullscreen = false;
		}
	}
	
	public void destroy() {
		GLFW.glfwDestroyWindow(window);
	}

	public long getId() {
		return window;
	}
	
	public void close() {
		GLFW.glfwSetWindowShouldClose(window, true);
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
}