package engine.ui.components;

import java.util.ArrayList;
import java.util.List;

import engine.core.Logger;
import engine.graphics.Renderer;
import engine.ui.constraints.Anchor;

public abstract class UIComponent {
	protected float x, y, width, height;
	protected UIComponent parent;
	protected List<UIComponent> children = new ArrayList<>();
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
	
	public void add(UIComponent child) {
        children.add(child);
    }
	
	protected abstract void drawSelf(Renderer r);

    public void render(Renderer r) {
        drawSelf(r);
        for (UIComponent c : children) {
            c.render(r);
        }
    }

	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
		if (parent == null) return;

		switch (anchor) {
		case CENTER:
			x = parent.width / 2f - width / 2f;
			y = parent.height / 2f - height / 2f;
			break;
		case BOTTOM_LEFT:
			x = 0;
			y = parent.height - height;
			break;
		case BOTTOM_CENTER:
			x = parent.width / 2f - width / 2f;
			y = parent.height - height;
			break;
		case BOTTOM_RIGHT:
			x = parent.width - width;
			y = parent.height - height;
			break;
		case TOP_LEFT:
			x = 0;
			y = 0;
			break;
		case TOP_CENTER:
			x = parent.width / 2f - width / 2f;
			y = 0;
			break;
		case TOP_RIGHT:
			x = parent.width - width;
			y = 0;
			break;
		default:
			Logger.warn("No anchor rule for : " + anchor);
			break;
		}
	}

	public float getAbsoluteX() {
		return parent == null ? x : parent.getAbsoluteX() + x;
	}

	public float getAbsoluteY() {
		return parent == null ? y : parent.getAbsoluteY() + y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}