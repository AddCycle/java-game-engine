package engine.inputs.keybinds;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import engine.entities.movement.Direction;
import engine.inputs.Inputs;
import engine.inputs.action.Action;

/**
 * Just a placeholder class constructor to give an sample on how to bind actions to keys
 * FIXME : this class needs to be fixed as the main entry of the game needs to know all the keys at one place
 * TODO : make the axis controls some actions too
 */
public class Keybinds {
	protected Map<Action, Integer> keys = new HashMap<>();
    protected Map<Action, Integer> padButtons = new HashMap<>();
    protected Map<Action, Direction> padAxis = new HashMap<>(); // FIXME maybe make a proper one or convert to <action,callback>

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
        
        padAxis.put(Action.MOVE_LEFT, Direction.LEFT);
        padAxis.put(Action.MOVE_RIGHT, Direction.RIGHT);
        padAxis.put(Action.MOVE_UP, Direction.UP);
        padAxis.put(Action.MOVE_DOWN, Direction.DOWN);
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
//            || (padAxis.get(action) != null ? input.isAxisDown(padAxis.get(action)) : false);
    }

    public boolean isJustPressed(Inputs input, Action action) {
        return input.isKeyJustPressed(keys.get(action))
            || (padButtons.get(action) != null ? input.isPadButtonJustPressed(padButtons.get(action)) : false)
            || (padAxis.get(action) != null ? input.isAxisJustPressed(padAxis.get(action)) : false);
    }
}