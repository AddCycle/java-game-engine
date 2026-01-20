package engine.ui.components;

import java.util.ArrayList;
import java.util.List;

import engine.core.Logger;
import engine.graphics.Renderer;
import engine.ui.constraints.Anchor;
import engine.ui.constraints.Margin;
import engine.ui.constraints.Padding;

public abstract class UIComponent {
	protected float x, y, width, height;
	protected UIComponent parent;
	protected List<UIComponent> children = new ArrayList<>();
	protected Anchor anchor;
	protected Margin margin = Margin.ZERO;
	protected Padding padding = Padding.ZERO;

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
	
	public void remove(int index) {
		children.remove(index);
	}
	
	protected abstract void drawSelf(Renderer r);

    public void render(Renderer r) {
        drawSelf(r);
        for (UIComponent c : children) {
            c.render(r);
        }
    }

    /**
     * Sets spatial-properties relative to the parent component <br>
     * <b>IMPORTANT</b> should be called last
     * @param anchor
     */
	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
		if (parent == null) return;
		
		switch (anchor) {
		case CENTER_LEFT:
			x = 0;
			y = parent.getInnerHeight() / 2f - height / 2f;
			x += margin.left;
			break;
		case CENTER:
			x = parent.getInnerWidth() / 2f - width / 2f;
			y = parent.getInnerHeight() / 2f - height / 2f;
			break;
		case CENTER_RIGHT:
			x = parent.getInnerWidth() - width;
			y = parent.getInnerHeight() / 2f - height / 2f;
			x -= margin.right;
			break;
		case BOTTOM_LEFT:
			x = 0;
			y = parent.getInnerHeight() - height;
			x += margin.left;
			y -= margin.down;
			break;
		case BOTTOM_CENTER:
			x = parent.getInnerWidth() / 2f - width / 2f;
			y = parent.getInnerHeight() - height;
			y -= margin.down;
			break;
		case BOTTOM_RIGHT:
			x = parent.getInnerWidth() - width;
			y = parent.getInnerHeight() - height;
			x -= margin.right;
			y -= margin.down;
			break;
		case TOP_LEFT:
			x = 0;
			y = 0;
			x += margin.left;
			y += margin.up;
			break;
		case TOP_CENTER:
			x = parent.getInnerWidth() / 2f - width / 2f;
			y = 0;
			y += margin.up;
			break;
		case TOP_RIGHT:
			x = parent.getInnerWidth() - width;
			y = 0;
			x -= margin.right;
			y += margin.up;
			break;
		default:
			Logger.warn("No anchor rule for : " + anchor);
			break;
		}
	}
	
	/**
	 * Must be called before setAnchor
	 * @param margin
	 */
	public void setMargin(Margin margin) {
		this.margin = margin;
	}

	/**
	 * Must be called before setAnchor
	 * @param padding
	 */
	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	public float getAbsoluteX() {
	    float parentX = parent == null ? 0 : parent.getAbsoluteX();
	    float padLeft = parent == null ? 0 : parent.padding.left;
	    return parentX + x + padLeft;
	}

	public float getAbsoluteY() {
	    float parentY = parent == null ? 0 : parent.getAbsoluteY();
	    float padTop = parent == null ? 0 : parent.padding.up;
	    return parentY + y + padTop;
	}
	
	public float getInnerWidth() {
	    return width - padding.left - padding.right;
	}

	public float getInnerHeight() {
	    return height - padding.up - padding.down;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}