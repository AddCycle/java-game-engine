package engine.loader;

public class FontLoader {
	private int fontTexture = -1;
	private Loader loader;

	public FontLoader(Loader loader) {
		this.loader = loader;
	}

	/**
	 * Fonts are getting destroyed by calling
	 * {@link TextureLoader.destroyTextures()}
	 * @param path
	 */
	public void loadFont(String path) {
	    fontTexture = loader.getTextureLoader().loadTexture(path);
	}
	
	public void loadDefaultFont() {
	    loadFont("resources/assets/font.png");
	}
	
	public int getFontTexture() {
		return fontTexture;
	}
}
