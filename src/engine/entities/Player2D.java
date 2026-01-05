package engine.entities;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.world.Camera;

public class Player2D extends Entity {
	private float speed = 60f;
	private Inputs input;

    public Player2D(Inputs input, float x, float y, int texture) {
        super(x, y, 16, 16, texture);
        this.input = input;
    }

    @Override
    public void update(float dt) {
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) x -= speed * dt;
        if (input.isKeyDown(GLFW.GLFW_KEY_D)) x += speed * dt;
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) y -= speed * dt;
        if (input.isKeyDown(GLFW.GLFW_KEY_S)) y += speed * dt;
    }

    @Override
    public void render(Renderer renderer, Camera camera) {
    		float camX = camera.x;
    		float camY = camera.y;
        renderer.drawTexturedRect(texture, x - camX, y - camY, width, height);
    }
}