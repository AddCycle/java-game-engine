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
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;

import java.nio.FloatBuffer;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import engine.core.Logger;
import engine.inputs.action.Action;

public class Inputs {
	private long window;

	private boolean[] prevKeys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	private boolean[] padButtons = new boolean[GLFW.GLFW_GAMEPAD_BUTTON_LAST+1];
	private boolean[] prevPadButtons = new boolean[GLFW.GLFW_GAMEPAD_BUTTON_LAST+1];

	private double mouseX, mouseY;

	private static final Map<Integer, String> KEY_NAMES = Map.ofEntries(Map.entry(GLFW_KEY_UP, "UP"),
			Map.entry(GLFW_KEY_DOWN, "DOWN"), Map.entry(GLFW_KEY_LEFT, "LEFT"), Map.entry(GLFW_KEY_RIGHT, "RIGHT"),
			Map.entry(GLFW_KEY_LEFT_SHIFT, "L_SHIFT"), Map.entry(GLFW_KEY_RIGHT_SHIFT, "R_SHIFT"),
			Map.entry(GLFW_KEY_TAB, "TAB"), Map.entry(GLFW_KEY_ENTER, "ENTER"),
			Map.entry(GLFW_KEY_BACKSPACE, "BACKSPACE"), Map.entry(GLFW.GLFW_KEY_SPACE, "SPACE"),
			Map.entry(GLFW.GLFW_KEY_ESCAPE, "ESCAPE"));
	
	private static final String[] GAMEPAD_BUTTON_NAMES = {
		    "A",        // 0
		    "B",        // 1
		    "X",        // 2
		    "Y",        // 3
		    "LB",       // 4
		    "RB",       // 5
		    "BACK",     // 6
		    "START",    // 7
		    "GUIDE",    // 8
		    "L_STICK",  // 9
		    "R_STICK",  // 10
		    "DPAD_UP",  // 11
		    "DPAD_RIGHT",// 12
		    "DPAD_DOWN", // 13
		    "DPAD_LEFT"  // 14
		};

	public Inputs(long window) {
		this.window = window;

		setCallbacks();
	}
	
	public void setCallbacks() {

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
				String keyName = KEY_NAMES.getOrDefault(key, GLFW.glfwGetKeyName(key, scancode));
				keys[key] = action != GLFW.GLFW_RELEASE;
				if (action == GLFW.GLFW_PRESS) {
					Logger.debug(String.format("key pressed : %s", keyName));
				} else if (action == GLFW.GLFW_RELEASE) {
					Logger.debug(String.format("key released : %s", keyName));
				}
			}
		});
		
		// mouse buttons
		GLFW.glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
			if (button >= 0 && button < mouseButtons.length) {
				mouseButtons[button] = action != GLFW.GLFW_RELEASE;
				if (action == GLFW.GLFW_PRESS) {
					Logger.debug(String.format("mouse button pressed : %d", button));
				} else if (action == GLFW.GLFW_RELEASE) {
					Logger.debug(String.format("mouse button released : %d", button));
				}
			}
		});

		// mouse pos
		GLFW.glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
			mouseX = xpos;
			mouseY = ypos;
//			Logger.debug("mouseX,mouseY: %f, %f", mouseX, mouseY);
		});
	}
	
	private void pollGamepad(int jid) {
	    if (!GLFW.glfwJoystickPresent(jid)) return;
	    if (!GLFW.glfwJoystickIsGamepad(jid)) return;

	    GLFWGamepadState state = GLFWGamepadState.create();
	    GLFW.glfwGetGamepadState(jid, state);

	    for (int i = 0; i < padButtons.length; i++) {
	    		boolean pressed = state.buttons(i) == GLFW.GLFW_PRESS;
	        padButtons[i] = pressed;
	        if (pressed && !prevPadButtons[i]) {
	            Logger.debug("Gamepad button pressed: %s", getGamepadButton(i));
	        } else if (!pressed && prevPadButtons[i]) {
	            Logger.debug("Gamepad button released: %s", getGamepadButton(i));
	        }
	    }
	}
	
	public String getGamepadButton(int gamepadButton) {
	    return GAMEPAD_BUTTON_NAMES[gamepadButton];
	}

	public void update() {
		// keys
	    for (int i = 0; i < keys.length; i++) {
	        prevKeys[i] = keys[i]; // store state for next frame
	    }
	    
	    // gamepad
	    for (int i = 0; i < padButtons.length; i++) {
	        prevPadButtons[i] = padButtons[i];
	    }
	    
	    pollGamepad(GLFW.GLFW_JOYSTICK_1);
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

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	// gamepad query
	public boolean isPadButtonDown(int button) {
	    return padButtons[button];
	}

	public boolean isPadButtonJustPressed(int button) {
	    return padButtons[button] && !prevPadButtons[button];
	}

//	// Poll gamepads/controllers
//	public boolean isControllerButtonDown(int jid, int button) {
//		if (glfwJoystickPresent(jid)) {
//			GLFWGamepadState state = GLFWGamepadState.create();
//			if (GLFW.glfwJoystickIsGamepad(jid)) {
//				GLFW.glfwGetGamepadState(jid, state);
//				return state.buttons(button) == GLFW.GLFW_PRESS;
//			} else {
//				ByteBuffer buttons = glfwGetJoystickButtons(jid);
//				if (buttons != null && button < buttons.limit()) {
//					return buttons.get(button) == GLFW.GLFW_PRESS;
//				}
//			}
//		}
//		return false;
//	}

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
