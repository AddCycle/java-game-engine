package engine.loader;

public class Loader {
	private TextureLoader texLoader;

	public Loader() {
		texLoader = new TextureLoader();
	}
	
	public void destroy() {
		texLoader.destroyTextures();
	}

	public TextureLoader getTextureLoader() {
		return texLoader;
	}
}
