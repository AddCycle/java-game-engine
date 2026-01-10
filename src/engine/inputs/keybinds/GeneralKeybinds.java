package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class GeneralKeybinds extends Keybinds {

	public GeneralKeybinds() {
		keys.put(Action.TAB, GLFW.GLFW_KEY_TAB);
		padButtons.put(Action.TAB, GLFW.GLFW_GAMEPAD_BUTTON_BACK);
		keys.put(Action.FULLSCREEN, GLFW.GLFW_KEY_F11);
	}
}
