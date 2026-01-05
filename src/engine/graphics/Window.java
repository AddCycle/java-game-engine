package engine.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import engine.core.Logger;

public class Window {
	private long window;
	private int width, height;
	private String title;
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public boolean create() {
		if (!GLFW.glfwInit()) {
			Logger.error("Failed to create window !");
			return false;
		}

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		centerWindow();
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GLFW.glfwShowWindow(window);

		Logger.info("Window created successfully");
		
		return true;
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
		} else {
			GLFW.glfwSetWindowMonitor(window, monitor, 0, 0, width, height, mode.refreshRate());
		}
	}
	
	public void destroy() {
		GLFW.glfwDestroyWindow(window);
	}

	public long getId() {
		return window;
	}
}