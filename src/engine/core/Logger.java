package engine.core;

public class Logger {
	public static void info(String msg, Object... args) {
		System.out.println("[ENGINE/INFO] " + String.format(msg, args));
	}

	public static void warn(String msg, Object... args) {
		System.err.println("[ENGINE/WARN]" + String.format(msg, args));
	}

	public static void error(String msg, Object... args) {
		System.err.println("[ENGINE/ERROR]" + String.format(msg, args));
	}
}