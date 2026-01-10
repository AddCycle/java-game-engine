package engine.inputs.keybinds;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

public class PlatformerKeybinds extends Keybinds {

	public PlatformerKeybinds() {
        keys.put(Action.MOVE_LEFT, GLFW.GLFW_KEY_A);
        keys.put(Action.MOVE_RIGHT, GLFW.GLFW_KEY_D);
//        keys.put(Action.MOVE_UP, GLFW.GLFW_KEY_W);
//        keys.put(Action.MOVE_DOWN, GLFW.GLFW_KEY_S);
        keys.put(Action.JUMP, GLFW.GLFW_KEY_SPACE);
        keys.put(Action.INTERACT, GLFW.GLFW_KEY_ENTER);

        padButtons.put(Action.MOVE_LEFT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT);
        padButtons.put(Action.MOVE_RIGHT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT);
//        padButtons.put(Action.MOVE_UP, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP);
//        padButtons.put(Action.MOVE_DOWN, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN);
        padButtons.put(Action.JUMP, GLFW.GLFW_GAMEPAD_BUTTON_A);
        padButtons.put(Action.INTERACT, GLFW.GLFW_GAMEPAD_BUTTON_Y);
	}
}
