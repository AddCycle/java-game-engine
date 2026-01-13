package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class MenuKeybinds extends Keybinds {

	public MenuKeybinds() {
        keys.put(Action.MOVE_LEFT, GLFW.GLFW_KEY_LEFT);
        keys.put(Action.MOVE_RIGHT, GLFW.GLFW_KEY_RIGHT);
        keys.put(Action.MOVE_UP, GLFW.GLFW_KEY_UP);
        keys.put(Action.MOVE_DOWN, GLFW.GLFW_KEY_DOWN);
        keys.put(Action.CONFIRM, GLFW.GLFW_KEY_SPACE);

        padButtons.put(Action.MOVE_LEFT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT);
        padButtons.put(Action.MOVE_RIGHT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT);
        padButtons.put(Action.MOVE_UP, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP);
        padButtons.put(Action.MOVE_DOWN, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN);
        padButtons.put(Action.CONFIRM, GLFW.GLFW_GAMEPAD_BUTTON_A);
	}
}
