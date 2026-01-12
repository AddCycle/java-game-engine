package game;

import engine.animations.Animation;
import engine.entities.AnimatedPlayer2D;
import engine.graphics.Renderer;

public class AnimationsPresets {

	public static void topdown(AnimatedPlayer2D player, Renderer renderer) {
		int idle_down = renderer.loadTexture("resources/idle_down.png");
		int down1 = renderer.loadTexture("resources/down1.png");
		int down2 = renderer.loadTexture("resources/down2.png");

		int idle_up = renderer.loadTexture("resources/idle_up.png");
		int up1 = renderer.loadTexture("resources/up1.png");
		int up2 = renderer.loadTexture("resources/up2.png");

		int idle_left = renderer.loadTexture("resources/idle_left.png");
		int left1 = renderer.loadTexture("resources/left1.png");
		int left2 = renderer.loadTexture("resources/left2.png");

		int idle_right = renderer.loadTexture("resources/idle_right.png");
		int right1 = renderer.loadTexture("resources/right1.png");
		int right2 = renderer.loadTexture("resources/right2.png");

		Animation idleDown = new Animation(new int[] { idle_down }, 0.2f, true);
		Animation walkDown = new Animation(new int[] { down1, down2 }, 0.4f, true);

		Animation idleUp = new Animation(new int[] { idle_up }, 0.2f, true);
		Animation walkUp = new Animation(new int[] { up1, up2 }, 0.4f, true);

		Animation idleLeft = new Animation(new int[] { idle_left }, 0.2f, true);
		Animation walkLeft = new Animation(new int[] { left1, left2 }, 0.4f, true);

		Animation idleRight = new Animation(new int[] { idle_right }, 0.2f, true);
		Animation walkRight = new Animation(new int[] { right1, right2 }, 0.4f, true);

		player.addAnimation("idle_down", idleDown);
		player.addAnimation("walk_down", walkDown);

		player.addAnimation("idle_up", idleUp);
		player.addAnimation("walk_up", walkUp);

		player.addAnimation("idle_left", idleLeft);
		player.addAnimation("walk_left", walkLeft);

		player.addAnimation("idle_right", idleRight);
		player.addAnimation("walk_right", walkRight);
	}
}