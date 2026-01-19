package engine.loader;

public class Loader {
	private TextureLoader texLoader;
	private FontLoader fontLoader;

	public Loader() {
		texLoader = new TextureLoader(this);
		fontLoader = new FontLoader(this);
	}
	
	/**
	 * Be careful this method should destroy everything (mem leaks warning)
	 */
	public void destroy() {
		texLoader.destroyTextures();
	}

	public TextureLoader getTextureLoader() {
		return texLoader;
	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}
}
