package engine.ui.constraints;

public class Padding {
	public float left, right, up, down;
	
	public Padding(float padding) {
		left = padding;
		right = padding;
		up = padding;
		down = padding;
	}

	public Padding(float left, float right, float up, float down) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}
	
	public static final Padding ZERO = new Padding(0, 0, 0, 0);
}
