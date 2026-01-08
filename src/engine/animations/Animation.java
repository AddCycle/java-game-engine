package engine.animations;

public class Animation {
	private int[] frames; // texture IDs or tile indices
    private float frameTime; // seconds per frame
    private boolean loop;
    private float timer;
    private int currentFrame;

    public Animation(int[] frames, float frameTime, boolean loop) {
        this.frames = frames;
        this.frameTime = frameTime;
        this.loop = loop;
        this.timer = 0;
        this.currentFrame = 0;
    }

    public void update(float dt) {
        timer += dt;
        if (timer >= frameTime) {
            timer -= frameTime;
            currentFrame++;
            if (currentFrame >= frames.length) {
                if (loop) currentFrame = 0;
                else currentFrame = frames.length - 1;
            }
        }
    }

    public int getCurrentFrame() {
        return frames[currentFrame];
    }

    public void reset() {
        currentFrame = 0;
        timer = 0;
    }
}