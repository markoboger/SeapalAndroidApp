package de.htwg.seapal.utils.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import de.htwg.seapal.controller.IAccountController;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.controller.impl.AccountController;
import de.htwg.seapal.controller.impl.MainController;
import de.htwg.seapal.controller.impl.PersonController;
import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.database.IRaceDatabase;
import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.database.impl.TouchDBAccountDatabase;
import de.htwg.seapal.database.impl.TouchDBBoatDatabase;
import de.htwg.seapal.database.impl.TouchDBMarkDatabase;
import de.htwg.seapal.database.impl.TouchDBPersonDatabase;
import de.htwg.seapal.database.impl.TouchDBRaceDatabase;
import de.htwg.seapal.database.impl.TouchDBRouteDatabase;
import de.htwg.seapal.database.impl.TouchDBTripDatabase;
import de.htwg.seapal.database.impl.TouchDBWaypointDatabase;
import de.htwg.seapal.utils.logging.ILogger;
import de.htwg.seapal.utils.logging.Logger;

public class ImplModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(ILogger.class).to(Logger.class);

		binder.bind(IBoatDatabase.class).to(TouchDBBoatDatabase.class);

		binder.bind(IMarkDatabase.class).to(TouchDBMarkDatabase.class);

		binder.bind(IPersonDatabase.class).to(TouchDBPersonDatabase.class);

		binder.bind(IRouteDatabase.class).to(TouchDBRouteDatabase.class);

		binder.bind(ITripDatabase.class).to(TouchDBTripDatabase.class);

		binder.bind(IWaypointDatabase.class).to(TouchDBWaypointDatabase.class);

        binder.bind(IAccountDatabase.class).to(TouchDBAccountDatabase.class);

        binder.bind(IRaceDatabase.class).to(TouchDBRaceDatabase.class);

        binder.bind(IMainController.class).to(MainController.class).in(Scopes.SINGLETON);
        binder.bind(IPersonController.class).to(PersonController.class);
        binder.bind(IAccountController.class).to(AccountController.class);


	}

}