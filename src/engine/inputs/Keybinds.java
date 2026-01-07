package engine.inputs;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import engine.inputs.action.Action;

// FIXME : need to separate the actions between topdown world and platformer world, so between worlds
public class Keybinds {
	private Map<Action, Integer> keys = new HashMap<>();
    private Map<Action, Integer> padButtons = new HashMap<>();

    public Keybinds() {
        keys.put(Action.MOVE_LEFT, GLFW.GLFW_KEY_A);
        keys.put(Action.MOVE_RIGHT, GLFW.GLFW_KEY_D);
        keys.put(Action.MOVE_UP, GLFW.GLFW_KEY_W);
        keys.put(Action.MOVE_DOWN, GLFW.GLFW_KEY_S);
        keys.put(Action.JUMP, GLFW.GLFW_KEY_SPACE);
        keys.put(Action.INTERACT, GLFW.GLFW_KEY_ENTER);

        padButtons.put(Action.MOVE_LEFT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT);
        padButtons.put(Action.MOVE_RIGHT, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT);
        padButtons.put(Action.MOVE_UP, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP);
        padButtons.put(Action.MOVE_DOWN, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN);
        padButtons.put(Action.JUMP, GLFW.GLFW_GAMEPAD_BUTTON_A);
        padButtons.put(Action.INTERACT, GLFW.GLFW_GAMEPAD_BUTTON_X);
    }
    
    public void bindKey(Action action, int key) {
        keys.put(action, key);
    }

    public void bindPad(Action action, int button) {
        padButtons.put(action, button);
    }

    public boolean isDown(Inputs input, Action action) {
        return input.isKeyDown(keys.get(action))
            || input.isPadButtonDown(padButtons.get(action));
    }

    public boolean isJustPressed(Inputs input, Action action) {
        return input.isKeyJustPressed(keys.get(action))
            || input.isPadButtonDown(padButtons.get(action));
    }
}
