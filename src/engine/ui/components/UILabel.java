package engine.ui.components;

import engine.graphics.Renderer;
import engine.ui.constraints.Anchor;

public class UILabel extends UIComponent {
	protected String text;

	public UILabel(float x, float y, UIComponent parent) {
		super(x, y, 0, 0, parent);
	}

	/**
	 * Sets the text and corrects the dimensions for the layout as width, height
	 * Should be called before <br> {@link engine.ui.components.UILabel#setAnchor(Anchor)}
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
		int[] dims = Renderer.calculateTextDimensions(text);
		this.width = dims[0];
		this.height = dims[1];
	}

	@Override
	protected void drawSelf(Renderer r) {
		r.drawText(text != null ? text : "empty_label", getAbsoluteX(), getAbsoluteY());
	}
}