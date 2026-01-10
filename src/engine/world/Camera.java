package engine.world;

import engine.entities.Entity;
import engine.math.MathHelper;

public class Camera {
	public float x, y;
	public float width, height;
	private Entity followedEntity;
	private World2D world;
	private float smoothing;

	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
		this.smoothing = 0.1f;
	}

	public void setFollowEntity(Entity e, float smoothing) {
		this.smoothing = smoothing;
		this.followedEntity = e;
		x = followedEntity.x + followedEntity.width / 2f - width / 2f;
		y = followedEntity.y + followedEntity.height / 2f - height / 2f;
	}

	public void update(float dt) {
	    if (followedEntity != null) {
	        float targetX = followedEntity.x + followedEntity.width / 2f - width / 2f;
	        float targetY = followedEntity.y + followedEntity.height / 2f - height / 2f;

	        x = MathHelper.lerp(x, targetX, smoothing);
	        y = MathHelper.lerp(y, targetY, smoothing);
	    }

	    if (world != null) {
	        x = Math.max(0, Math.min(x, world.getWidth() - width));
	        y = Math.max(0, Math.min(y, world.getHeight() - height));
	    }
	}
	
	public void setWorld(World2D world) {
		this.world = world;
	}
}
