package inputs;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Logger;
import engine.inputs.Inputs;

public class InputTesting {
	public void test1(Engine engine) {

		Inputs input = engine.getInput();
		
		if (input.isControllerButtonDown(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)) {
			Logger.info("pressed left");
		}
		if (input.isControllerButtonDown(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT)) {
			Logger.info("pressed right");
		}
		if (input.isControllerButtonDown(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP)) {
			Logger.info("pressed up");
		}
		if (input.isControllerButtonDown(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN)) {
			Logger.info("pressed down");
		}
		
		float axisX = input.getControllerAxis(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_X);
		float axisY = input.getControllerAxis(GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y);
		Logger.info("x,y: %f,%f", axisX, axisY);
	}
}
