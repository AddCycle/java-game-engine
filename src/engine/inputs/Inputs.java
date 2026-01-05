package engine.inputs;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import engine.core.Engine;
import engine.core.Logger;

public class Inputs {
	private long window;

	private boolean[] prevKeys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] prevMouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

	private double mouseX, mouseY;

	private static final Map<Integer, String> KEY_NAMES = Map.ofEntries(Map.entry(GLFW_KEY_UP, "UP"),
			Map.entry(GLFW_KEY_DOWN, "DOWN"), Map.entry(GLFW_KEY_LEFT, "LEFT"), Map.entry(GLFW_KEY_RIGHT, "RIGHT"),
			Map.entry(GLFW_KEY_LEFT_SHIFT, "L_SHIFT"), Map.entry(GLFW_KEY_RIGHT_SHIFT, "R_SHIFT"),
			Map.entry(GLFW_KEY_TAB, "TAB"), Map.entry(GLFW_KEY_ENTER, "ENTER"),
			Map.entry(GLFW_KEY_BACKSPACE, "BACKSPACE"), Map.entry(GLFW.GLFW_KEY_SPACE, "SPACE"));

	public Inputs(long window) {
		this.window = window;

		GLFW.glfwSetJoystickCallback((jid, event) -> {
			if (event == GLFW.GLFW_CONNECTED) {
				Logger.info("Joystick connected : %d", jid);
			} else if (event == GLFW.GLFW_DISCONNECTED) {
				Logger.info("Joystick disconnected : %d", jid);
			}
		});

		// keyboard
		GLFW.glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
			if (key != GLFW.GLFW_KEY_UNKNOWN && key >= 0 && key < keys.length) {
				keys[key] = action != GLFW.GLFW_RELEASE;
				if (!Engine.debug())
					return;
				String keyName = KEY_NAMES.getOrDefault(key, GLFW.glfwGetKeyName(key, scancode));
				if (action == GLFW.GLFW_PRESS) {
					Logger.info(String.format("key pressed : %s", keyName));
				} else {
					Logger.info(String.format("key released : %s", keyName));
				}
			}
		});

		// mouse buttons
		GLFW.glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
			if (button >= 0 && button < mouseButtons.length) {
				mouseButtons[button] = action != GLFW.GLFW_RELEASE;
				if (!Engine.debug())
					return;
				if (action == GLFW.GLFW_PRESS) {
					Logger.info(String.format("mouse button pressed : %d", button));
				} else {
					Logger.info(String.format("mouse button released : %d", button));
				}
			}
		});

		// mouse pos
		GLFW.glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
			mouseX = xpos;
			mouseY = ypos;
		});
	}

	// Keyboard query
	public boolean isKeyDown(int key) {
		return keys[key];
	}

	public boolean isKeyJustPressed(int key) {
	    return keys[key] && !prevKeys[key];
	}

	// Mouse query
	public boolean isMouseButtonDown(int button) {
		return mouseButtons[button];
	}

	public boolean isMouseButtonJustPressed(int button) {
	    return mouseButtons[button] && !prevMouseButtons[button];
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	// Poll gamepads/controllers
	public boolean isControllerButtonDown(int jid, int button) {
		if (glfwJoystickPresent(jid)) {
			GLFWGamepadState state = GLFWGamepadState.create();
			if (GLFW.glfwJoystickIsGamepad(jid)) {
				GLFW.glfwGetGamepadState(jid, state);
				return state.buttons(button) == GLFW.GLFW_PRESS;
			} else {
				ByteBuffer buttons = glfwGetJoystickButtons(jid);
				if (buttons != null && button < buttons.limit()) {
					return buttons.get(button) == GLFW.GLFW_PRESS;
				}
			}
		}
		return false;
	}

	public float getControllerAxis(int jid, int axis) {
		if (glfwJoystickPresent(jid)) {
			GLFWGamepadState state = GLFWGamepadState.create();
			if (GLFW.glfwJoystickIsGamepad(jid)) {
				GLFW.glfwGetGamepadState(jid, state);
				return state.axes(axis);
			}
			FloatBuffer axes = GLFW.glfwGetJoystickAxes(jid);
			if (axes != null && axis < axes.limit()) {
				return axes.get(axis);
			}
		}
		return 0f;
	}
}
