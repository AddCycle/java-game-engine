package game;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;

import engine.core.Engine;
import engine.core.Game;
import engine.core.Logger;
import engine.inputs.Inputs;

public class NamelessAdventure implements Game {
	private Engine engine;

	public NamelessAdventure(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

//	    if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
//	    	Logger.info("Pressed key W");
//	    }
//	    if (input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_A)) {
//	    	Logger.info("Pressing the gamepad button : A");
//	    }
//
//	    if (input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)) {
//	    	Logger.info("Pressing the gamepad button : LEFT");
//	    }
//	    if (input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT)) {
//	    	Logger.info("Pressing the gamepad button : RIGHT");
//	    }
//	    if (input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP)) {
//	    	Logger.info("Pressing the gamepad button : UP");
//	    }
//	    if (input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN)) {
//	    	Logger.info("Pressing the gamepad button : DOWN");
//	    }
	    
	    float dpadX = input.getControllerAxis(GLFW_JOYSTICK_1, 4);
	    float dpadY = -input.getControllerAxis(GLFW_JOYSTICK_1, 5); // invert Y to match stick convention

	    if (dpadX < -0.5f) Logger.info("D-Pad LEFT");
	    if (dpadX > 0.5f)  Logger.info("D-Pad RIGHT");
	    if (dpadY > 0.5f)  Logger.info("D-Pad UP");
	    if (dpadY < -0.5f) Logger.info("D-Pad DOWN");
	    
//	    float axisX = input.getControllerAxis(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_X);
//	    float axisY = input.getControllerAxis(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y);
//	    Logger.info("x,y : " + axisX + "," + axisY);
//		if (GLFW.glfwJoystickPresent(GLFW_JOYSTICK_1)) {
//		    ByteBuffer buttons = GLFW.glfwGetJoystickButtons(GLFW_JOYSTICK_1);
//		    Logger.info("Buttons count: " + buttons.limit());
//		    for (int i = 0; i < buttons.limit(); i++) {
//		        Logger.info("Button " + i + ": " + buttons.get(i));
//		    }
//
//		    FloatBuffer axes = GLFW.glfwGetJoystickAxes(GLFW_JOYSTICK_1);
//		    Logger.info("Axes count: " + axes.limit());
//		    for (int i = 0; i < axes.limit(); i++) {
//		        Logger.info("Axis " + i + ": " + axes.get(i));
//		    }
//		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
}