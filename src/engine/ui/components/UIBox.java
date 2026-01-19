package engine.ui.components;

import java.awt.Color;

import engine.graphics.Renderer;

/**
 * Same as UIPanel but with an outline (border)
 */
public class UIBox extends UIComponent {
	private Color bgColor;
	private Color outlineColor;
	private float border = 1;

	public UIBox() {}

	public UIBox(float x, float y, float width, float height, UIComponent parent) {
		super(x, y, width, height, parent);
	}

	@Override
	protected void drawSelf(Renderer r) {
		Color oc = outlineColor != null ? outlineColor : Color.white;
		Color bgc = bgColor != null ? bgColor : Color.black;
		
		r.drawRect(getAbsoluteX(), getAbsoluteY(), width, height, oc);
		r.drawRect(getAbsoluteX() + border / 2, getAbsoluteY() + border / 2, width - border, height - border, bgc);
	}

	public float getBorder() {
		return border;
	}

	public void setBackgroundColor(Color c) {
		this.bgColor = c;
	}

	public void setOutlineColor(Color c) {
		this.outlineColor = c;
	}
	
	/**
	 * The minimal border is 1 and the maximal is max(width, height) of the box
	 * @param border
	 */
	public void setBorder(float border) {
		this.border = Math.clamp(border, 1, Math.min(width, height));
	}

}
