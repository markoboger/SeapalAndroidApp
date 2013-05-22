package de.htwg.seapal.utils.modules;

import com.google.inject.Binder;
import com.google.inject.Module;

import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.controller.IRouteController;
import de.htwg.seapal.controller.ITripController;
import de.htwg.seapal.controller.IWaypointController;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.controller.impl.MarkController;
import de.htwg.seapal.controller.impl.PersonController;
import de.htwg.seapal.controller.impl.RouteController;
import de.htwg.seapal.controller.impl.TripController;
import de.htwg.seapal.controller.impl.WaypointController;
import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.database.impl.TouchDBBoatDatabase;
import de.htwg.seapal.database.impl.TouchDBMarkDatabase;
import de.htwg.seapal.database.impl.TouchDBPersonDatabase;
import de.htwg.seapal.database.impl.TouchDBRouteDatabase;
import de.htwg.seapal.database.impl.TouchDBTripDatabase;
import de.htwg.seapal.database.impl.TouchDBWaypointDatabase;
import de.htwg.seapal.utils.logging.ILogger;
import de.htwg.seapal.utils.logging.Logger;

public class ImplModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(ILogger.class).to(Logger.class);

		binder.bind(IBoatController.class).to(BoatController.class);
		binder.bind(IBoatDatabase.class).to(TouchDBBoatDatabase.class);

		binder.bind(IMarkController.class).to(MarkController.class);
		binder.bind(IMarkDatabase.class).to(TouchDBMarkDatabase.class);

		binder.bind(IPersonController.class).to(PersonController.class);
		binder.bind(IPersonDatabase.class).to(TouchDBPersonDatabase.class);

		binder.bind(IRouteController.class).to(RouteController.class);
		binder.bind(IRouteDatabase.class).to(TouchDBRouteDatabase.class);

		binder.bind(ITripController.class).to(TripController.class);
		binder.bind(ITripDatabase.class).to(TouchDBTripDatabase.class);

		binder.bind(IWaypointController.class).to(WaypointController.class);
		binder.bind(IWaypointDatabase.class).to(TouchDBWaypointDatabase.class);
	}

}