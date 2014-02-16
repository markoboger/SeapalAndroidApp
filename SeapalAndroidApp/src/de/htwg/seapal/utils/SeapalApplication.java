package de.htwg.seapal.utils;

import android.app.Application;

import com.couchbase.lite.router.URLStreamHandlerFactory;

import de.htwg.seapal.utils.modules.ImplModule;
import roboguice.RoboGuice;

public class SeapalApplication extends Application {

    static {
        URLStreamHandlerFactory.registerSelfIgnoreError();

    }

	@Override
	public void onCreate() {
		super.onCreate();

		RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
				RoboGuice.newDefaultRoboModule(this), new ImplModule());
	}

}
