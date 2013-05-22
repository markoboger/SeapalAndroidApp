package de.htwg.seapal.utils;

import roboguice.RoboGuice;
import android.app.Application;
import de.htwg.seapal.utils.modules.ImplModule;

public class SeapalApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
				RoboGuice.newDefaultRoboModule(this), new ImplModule());
	}

}
