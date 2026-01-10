package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class PauseKeybinds extends Keybinds {

	public PauseKeybinds() {
		keys.put(Action.RESUME, GLFW.GLFW_KEY_SPACE);
		padButtons.put(Action.RESUME, GLFW.GLFW_GAMEPAD_BUTTON_CROSS);
		keys.put(Action.QUIT, GLFW.GLFW_KEY_ESCAPE);
	}
}
