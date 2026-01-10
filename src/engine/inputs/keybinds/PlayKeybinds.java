package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class PlayKeybinds extends Keybinds {
	
	public PlayKeybinds() {
		keys.put(Action.PAUSE, GLFW.GLFW_KEY_ESCAPE);
		padButtons.put(Action.PAUSE, GLFW.GLFW_GAMEPAD_BUTTON_START);
	}
}
