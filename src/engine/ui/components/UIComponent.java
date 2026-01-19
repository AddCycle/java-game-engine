package engine.ui.components;

import engine.graphics.Renderer;
import engine.ui.constraints.Anchor;

public abstract class UIComponent {
	protected float x, y, width, height;
	protected UIComponent parent;
	protected Anchor anchor;

	public UIComponent() {

	}

	public UIComponent(float x, float y, float width, float height, UIComponent parent) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	public abstract void render(Renderer renderer);

	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
		if (parent == null) return;

		switch (anchor) {
		case CENTER:
			x = parent.width / 2f - width / 2f;
			y = parent.height / 2f - height / 2f;
			break;
		case BOTTOM_LEFT:
			break;
		case BOTTOM_RIGHT:
			break;
		case TOP_LEFT:
			break;
		case TOP_RIGHT:
			break;
		default:
			break;
		}
	}

	public float getAbsoluteX() {
		return parent == null ? x : parent.getAbsoluteX() + x;
	}

	public float getAbsoluteY() {
		return parent == null ? y : parent.getAbsoluteY() + y;
	}
}