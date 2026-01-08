package engine.entities;

import java.util.HashMap;
import java.util.Map;

import engine.animations.Animation;
import engine.animations.AnimationStateMachine;
import engine.core.Logger;
import engine.graphics.Renderer;
import engine.world.Camera;

public class AnimatedEntity extends Entity {
	protected Animation currentAnimation;
	protected AnimationStateMachine stateMachine;
	private Map<String, Animation> animations = new HashMap<>();
	protected String state = "";

	public AnimatedEntity(float x, float y, float width, float height) {
		super(x, y, width, height, -1);
	}

	public void addAnimation(String state, Animation anim) {
		animations.put(state, anim);
		if (currentAnimation == null) {
			this.state = state;
			setAnimation(anim);
		}
	}

	public void setAnimation(Animation animation) {
		if (currentAnimation != animation) {
			currentAnimation = animation;
			currentAnimation.reset();
		}
	}

	public void setStateMachine(AnimationStateMachine sm) {
		this.stateMachine = sm;
	}

	@Override
	public void update(float dt) {
		if (stateMachine != null) {
			String next = stateMachine.compute(this);
			if (!next.equals(state) && animations.containsKey(next)) {
				state = next;
				setAnimation(animations.get(next));
			}
		} else {
			Logger.debug("state machine is null");
		}

		if (currentAnimation != null) {
			currentAnimation.update(dt);
			texture = currentAnimation.getCurrentFrame();
		}
	}

	@Override
	public void render(Renderer renderer, Camera camera) {
		renderer.drawTexturedRect(texture, x - camera.x, y - camera.y, width, height);
	}
}