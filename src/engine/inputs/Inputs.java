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

import java.awt.Point;
import java.nio.FloatBuffer;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWGamepadState;

import engine.core.Engine;
import engine.core.Logger;
import engine.entities.movement.Direction;
import engine.world.Camera;
import imgui.glfw.ImGuiImplGlfw;

public class Inputs {
	private Engine engine;
	private long window;

	private boolean[] prevKeys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] prevMouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	private boolean[] padButtons = new boolean[GLFW.GLFW_GAMEPAD_BUTTON_LAST+1];
	private boolean[] prevPadButtons = new boolean[GLFW.GLFW_GAMEPAD_BUTTON_LAST+1];
	
	private boolean axisLeft, axisRight, axisUp, axisDown;
	private boolean prevAxisLeft, prevAxisRight, prevAxisUp, prevAxisDown;

	private double mouseX, mouseY;

	private ImGuiImplGlfw imgui;

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

	public Inputs(Engine engine, ImGuiImplGlfw imgui) {
		this.engine = engine;
		this.window = engine.getWindow().getId();
		this.imgui = imgui;

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
			imgui.keyCallback(w, key, scancode, action, mods);
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
			imgui.mouseButtonCallback(w, button, action, mods);
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
			imgui.cursorPosCallback(window, xpos, ypos);
			mouseX = xpos;
			mouseY = ypos;
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
		prevAxisLeft  = axisLeft;
	    prevAxisRight = axisRight;
	    prevAxisUp    = axisUp;
	    prevAxisDown  = axisDown;

		// keys
	    for (int i = 0; i < keys.length; i++) {
	        prevKeys[i] = keys[i]; // store state for next frame
	    }

		// mouseButtons
	    for (int i = 0; i < mouseButtons.length; i++) {
	        prevMouseButtons[i] = mouseButtons[i];
	    }
	    
	    // gamepad
	    for (int i = 0; i < padButtons.length; i++) {
	        prevPadButtons[i] = padButtons[i];
	    }
	    
	    pollGamepad(GLFW.GLFW_JOYSTICK_1);
	    
	    float x = getControllerAxis(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_X);
	    float y = getControllerAxis(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y);

	    float DEAD = 0.5f;

	    axisLeft  = x < -DEAD;
	    axisRight = x >  DEAD;
	    axisUp    = y < -DEAD;
	    axisDown  = y >  DEAD;
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

	// gamepad query
	public boolean isPadButtonDown(int button) {
	    return padButtons[button];
	}

	public boolean isPadButtonJustPressed(int button) {
	    return padButtons[button] && !prevPadButtons[button];
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
	
	public boolean isAxisJustPressed(Direction dir) {
	    return switch (dir) {
	        case LEFT  -> axisLeft  && !prevAxisLeft;
	        case RIGHT -> axisRight && !prevAxisRight;
	        case UP    -> axisUp    && !prevAxisUp;
	        case DOWN  -> axisDown  && !prevAxisDown;
	    };
	}

	/**
	 * returns currentMousePosition inside the Window
	 * 
	 * @return Point(mouseX,mouseY);
	 */
	public Point getMousePos() {
	    Inputs input = engine.getInput();
	    Camera camera = engine.getCamera();

	    double mouseX = input.getMouseX();
	    double mouseY = input.getMouseY();

	    int winW = engine.getWindow().getWidth();
	    int winH = engine.getWindow().getHeight();

	    float scale = Math.min(
	        (float) winW / camera.width,
	        (float) winH / camera.height
	    );

	    int viewW = (int)(camera.width * scale);
	    int viewH = (int)(camera.height * scale);

	    int offsetX = (winW - viewW) / 2;
	    int offsetY = (winH - viewH) / 2;

	    // Convert to world space
	    double worldX = (mouseX - offsetX) / scale;
	    double worldY = (mouseY - offsetY) / scale;

	    return new Point((int) worldX, (int) worldY);
	}
	
	private void debugMouse() {
	    Inputs input = engine.getInput();
	    Camera camera = engine.getCamera();

	    double mouseX = input.getMouseX();
	    double mouseY = input.getMouseY();

	    int winW = engine.getWindow().getWidth();
	    int winH = engine.getWindow().getHeight();

	    float scale = Math.min(
	        (float) winW / camera.width,
	        (float) winH / camera.height
	    );

	    int viewW = (int)(camera.width * scale);
	    int viewH = (int)(camera.height * scale);

	    int offsetX = (winW - viewW) / 2;
	    int offsetY = (winH - viewH) / 2;

	    double worldX = (mouseX - offsetX) / scale;
	    double worldY = (mouseY - offsetY) / scale;

	    Logger.info("----- MOUSE DEBUG -----");
	    Logger.info("mouse raw   : %.2f , %.2f", mouseX, mouseY);
	    Logger.info("window      : %d x %d", winW, winH);
	    Logger.info("camera      : %.2f x %.2f", camera.width, camera.height);
	    Logger.info("scale       : %.4f", scale);
	    Logger.info("viewport    : %d x %d", viewW, viewH);
	    Logger.info("offset      : %d , %d", offsetX, offsetY);
	    Logger.info("world       : %.2f , %.2f", worldX, worldY);
	    Logger.info("------------------------");
	}
}
