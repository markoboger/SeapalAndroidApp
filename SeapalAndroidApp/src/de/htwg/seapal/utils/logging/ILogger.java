/**
 * 
 */
package de.htwg.seapal.utils.logging;

/**
 * Logging interface to use the same logging mechanism
 * on independent platforms.
 * @author Benjamin
 */
public interface ILogger {
	/**
	 * Logs an info/debug message.
	 * @param tag The tag of the log for separation.
	 * @param msg The message to log.
	 */
	void info(String tag, String msg);
	
	/**
	 * Logs a warning message.
	 * @param tag The tag of the log for separation.
	 * @param msg The message to log.
	 */
	void warn(String tag, String msg);
	
	/**
	 * Logs an error message.
	 * @param tag The tag of the log for separation.
	 * @param msg The message to log.
	 */
	void error(String tag, String msg);
}
