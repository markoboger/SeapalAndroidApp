package de.htwg.seapal.utils;

import android.app.Application;

import com.couchbase.lite.router.URLStreamHandlerFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.MapsInitializer;

import de.htwg.seapal.utils.modules.ImplModule;
import roboguice.RoboGuice;

public class SeapalApplication extends Application {

    static {
        URLStreamHandlerFactory.registerSelfIgnoreError();

    }

	@Override
	public void onCreate() {
		super.onCreate();
        System.setProperty("org.ektorp.support.AutoUpdateViewOnChange", "true");

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new ImplModule());
	}

}
