package engine.core;

public interface Game {
	public void init();
	public void update(float dt);
	public void debug(int fps);
}