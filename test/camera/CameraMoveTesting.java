package camera;

import org.lwjgl.glfw.GLFW;

import engine.inputs.Inputs;
import engine.world.Camera;

public class CameraMoveTesting {
	public static void test1(Camera camera, Inputs input, float dt) {
		
		if (input.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
			camera.x -= 5 * dt;
		}
		else if (input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
			camera.x += 5 * dt;
		}

		if (input.isKeyDown(GLFW.GLFW_KEY_UP)) {
			camera.y -= 5 * dt;
		}
		else if (input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
			camera.y += 5 * dt;
		}
	}

}
