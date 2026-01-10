package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class DialogKeybinds extends Keybinds {

	public DialogKeybinds() {
		keys.put(Action.CONFIRM, GLFW.GLFW_KEY_SPACE);
		padButtons.put(Action.CONFIRM, GLFW.GLFW_GAMEPAD_BUTTON_A);
	}

}