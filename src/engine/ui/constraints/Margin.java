package engine.ui.constraints;

public class Margin {
	public float left, right, up, down;
	
	public Margin(float margin) {
		left = margin;
		right = margin;
		up = margin;
		down = margin;
	}

	public Margin(float left, float right, float up, float down) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}

	public static final Margin ZERO = new Margin(0, 0, 0, 0);
}