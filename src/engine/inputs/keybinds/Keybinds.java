package engine.inputs.keybinds;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import engine.inputs.Inputs;
import engine.inputs.action.Action;

/**
 * Just a placeholder class constructor to give an sample on how to bind actions to keys
 */
public class Keybinds {
	protected Map<Action, Integer> keys = new HashMap<>();
    protected Map<Action, Integer> padButtons = new HashMap<>();

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
            || (padButtons.get(action) != null ? input.isPadButtonDown(padButtons.get(action)) : false);
    }

    public boolean isJustPressed(Inputs input, Action action) {
        return input.isKeyJustPressed(keys.get(action))
            || (padButtons.get(action) != null ? input.isPadButtonJustPressed(padButtons.get(action)) : false);
    }
}