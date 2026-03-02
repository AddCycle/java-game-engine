package engine.ui.mouse;

import org.lwjgl.glfw.GLFW;

public class Cursors {
	public static final long ARROW_CURSOR;
	public static final long IBEAM_CURSOR;
	public static final long CROSSHAIR_CURSOR;
	public static final long POINTING_HAND_CURSOR;
	public static final long RESIZE_EW_CURSOR;
	public static final long RESIZE_NS_CURSOR;
	public static final long RESIZE_NWSE_CURSOR;
	public static final long RESIZE_NESW_CURSOR;
	public static final long RESIZE_ALL_CURSOR;
	public static final long NOT_ALLOWED_CURSOR;

	static {
		ARROW_CURSOR = createCursor(GLFW.GLFW_ARROW_CURSOR);
		IBEAM_CURSOR = createCursor(GLFW.GLFW_IBEAM_CURSOR);
		CROSSHAIR_CURSOR = createCursor(GLFW.GLFW_CROSSHAIR_CURSOR);
		POINTING_HAND_CURSOR = createCursor(GLFW.GLFW_POINTING_HAND_CURSOR);
		RESIZE_EW_CURSOR = createCursor(GLFW.GLFW_RESIZE_EW_CURSOR);
		RESIZE_NS_CURSOR = createCursor(GLFW.GLFW_RESIZE_NS_CURSOR);
		RESIZE_NWSE_CURSOR = createCursor(GLFW.GLFW_RESIZE_NWSE_CURSOR);
		RESIZE_NESW_CURSOR = createCursor(GLFW.GLFW_RESIZE_NESW_CURSOR);
		RESIZE_ALL_CURSOR = createCursor(GLFW.GLFW_RESIZE_ALL_CURSOR);
		NOT_ALLOWED_CURSOR = createCursor(GLFW.GLFW_NOT_ALLOWED_CURSOR);
	}

	public static long createCursor(int cursor) {
		return GLFW.glfwCreateStandardCursor(cursor);
	}

	public static void destroyAll() {
		GLFW.glfwDestroyCursor(ARROW_CURSOR);
		GLFW.glfwDestroyCursor(IBEAM_CURSOR);
		GLFW.glfwDestroyCursor(CROSSHAIR_CURSOR);
		GLFW.glfwDestroyCursor(POINTING_HAND_CURSOR);
		GLFW.glfwDestroyCursor(RESIZE_EW_CURSOR);
		GLFW.glfwDestroyCursor(RESIZE_NS_CURSOR);
		GLFW.glfwDestroyCursor(RESIZE_NWSE_CURSOR);
		GLFW.glfwDestroyCursor(RESIZE_NESW_CURSOR);
		GLFW.glfwDestroyCursor(RESIZE_ALL_CURSOR);
		GLFW.glfwDestroyCursor(NOT_ALLOWED_CURSOR);
	}
}
