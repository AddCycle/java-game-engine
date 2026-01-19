package engine.math;

public class MathHelper {

	/**
	 * This function does linear interpolation between a and b with smoothing factor
	 * @param a
	 * @param b
	 * @param smoothing between 0 and 1
	 * @return
	 */
	public static float lerp(float a, float b, float smoothing) {
	    return a + (b - a) * smoothing;
	}
	
	/**
	 * This function returns a clamped color between 0 and 1 (useful for OpenGL colors)
	 * @param c
	 * @return
	 */
	public static float clampColor(float c) {
		return Math.clamp(c, 0f, 1f);
	}
}
