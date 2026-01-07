package engine.core;

public class Logger {
	public static Level level = Level.INFO;
	
	public static void setLevel(Level printLevel) {
		level = printLevel;
	}

	public static void info(String msg, Object... args) {
		if (level.priority >= Level.INFO.priority) {
			System.out.println("[ENGINE/INFO] " + String.format(msg, args));
		}
	}

	public static void warn(String msg, Object... args) {
		if (level.priority >= Level.WARN.priority) {
			System.err.println("[ENGINE/WARN] " + String.format(msg, args));
		}
	}

	public static void error(String msg, Object... args) {
		if (level.priority >= Level.ERROR.priority) {
			System.err.println("[ENGINE/ERROR] " + String.format(msg, args));
		}
	}

	public static void debug(String msg, Object... args) {
		if (level.priority >= Level.DEBUG.priority) {
			System.out.println("[ENGINE/DEBUG] " + String.format(msg, args));
		}
	}
	
	public static enum Level {
		ERROR(0),
		WARN(1),
		INFO(2),
		DEBUG(3);
		
		public int priority;
		
		Level(int prio) {
			priority = prio;
		}
	}
}