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
}
