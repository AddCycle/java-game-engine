package engine.world;

import engine.core.Logger;
import engine.entities.Entity;

public class Camera {
	public float x, y;
	public float width, height;
	private Entity followedEntity;

	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setFollowEntity(Entity e) {
		this.followedEntity = e;
		x = followedEntity.x + followedEntity.width / 2f - width / 2f;
		y = followedEntity.y + followedEntity.height / 2f - height / 2f;
	}

	public void update(float dt) {
		if (followedEntity != null) {
			x = followedEntity.x + followedEntity.width / 2f - width / 2f;
			y = followedEntity.y + followedEntity.height / 2f - height / 2f;
		}
		Logger.info("camX,camY: %f,%f", x, y);
		Logger.info("w,h: %f,%f", width, height);
	}
}
