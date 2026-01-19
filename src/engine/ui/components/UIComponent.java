package engine.ui.components;

import engine.graphics.Renderer;

public abstract class UIComponent {
	private float x, y, width, height;
	private UIComponent parent;
	
	public abstract void render(Renderer renderer);
	
	public float getAbsoluteX() {
	    return parent == null ? x : parent.getAbsoluteX() + x;
	}

	public float getAbsoluteY() {
	    return parent == null ? y : parent.getAbsoluteY() + y;
	}
}