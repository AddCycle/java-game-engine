package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class GeneralKeybinds extends Keybinds {

	public GeneralKeybinds() {
		keys.put(Action.TAB, GLFW.GLFW_KEY_TAB);
		padButtons.put(Action.TAB, GLFW.GLFW_GAMEPAD_BUTTON_START);
		keys.put(Action.FULLSCREEN, GLFW.GLFW_KEY_F11);
		keys.put(Action.CONFIRM, GLFW.GLFW_KEY_SPACE);
		padButtons.put(Action.CONFIRM, GLFW.GLFW_GAMEPAD_BUTTON_A);
		keys.put(Action.PAUSE, GLFW.GLFW_KEY_ESCAPE);
		padButtons.put(Action.PAUSE, GLFW.GLFW_GAMEPAD_BUTTON_CROSS);
	}
}
