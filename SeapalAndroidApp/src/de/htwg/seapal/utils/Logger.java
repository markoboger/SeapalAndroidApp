package de.htwg.seapal.utils;

import android.util.Log;
import de.htwg.seapal.utils.logging.ILogger;

public class Logger implements ILogger {

	@Override
	public void info(String tag, String msg) {
		Log.i(tag, msg);
	}

	@Override
	public void warn(String tag, String msg) {
		Log.i(tag, msg);
	}

	@Override
	public void error(String tag, String msg) {
		Log.i(tag, msg);
	}

}
