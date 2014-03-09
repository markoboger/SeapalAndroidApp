package de.htwg.seapal.utils.logging;

import android.util.Log;

public class Logger implements ILogger {

	@Override
	public void info(String tag, String msg) {
		Log.i(tag, msg);
	}

	@Override
	public void warn(String tag, String msg) {
		Log.w(tag, msg);
	}

	@Override
	public void error(String tag, String msg) {
		Log.e(tag, msg);
	}

    @Override
    public void exc(Exception e) {
        e.printStackTrace();
    }

}
