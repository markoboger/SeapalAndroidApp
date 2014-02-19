package de.htwg.seapal.utils.modules;

import android.content.Context;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

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
import de.htwg.seapal.database.impl.TouchDBHelper;
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

        binder.bind(IBoatDatabase.class).to(TouchDBBoatDatabase.class).in(Scopes.SINGLETON);

        binder.bind(IMarkDatabase.class).to(TouchDBMarkDatabase.class);

        binder.bind(IPersonDatabase.class).to(TouchDBPersonDatabase.class);

        binder.bind(IRouteDatabase.class).to(TouchDBRouteDatabase.class);

        binder.bind(ITripDatabase.class).to(TouchDBTripDatabase.class);

        binder.bind(IWaypointDatabase.class).to(TouchDBWaypointDatabase.class);

        binder.bind(IAccountDatabase.class).to(TouchDBAccountDatabase.class);

        binder.bind(IRaceDatabase.class).to(TouchDBRaceDatabase.class);

        binder.bind(IMainController.class).to(MainController.class).in(Scopes.SINGLETON);
        binder.bind(IPersonController.class).to(PersonController.class).in(Scopes.SINGLETON);
        binder.bind(IAccountController.class).to(AccountController.class).in(Scopes.SINGLETON);


        binder.bind(String.class).annotatedWith(Names.named("databaseOfAccount")).toInstance("seapal_account_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfPerson")).toInstance("seapal_person_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfBoat")).toInstance("seapal_boat_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfTrip")).toInstance("seapal_trip_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfWaypoint")).toInstance("seapal_waypoint_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfRoute")).toInstance("seapal_route_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfMark")).toInstance("seapal_mark_db");
        binder.bind(String.class).annotatedWith(Names.named("databaseOfRace")).toInstance("seapal_race_db");


    }


    @Provides
    @Named("accountCouchDbConnector")
    TouchDBHelper getAccountStdCouchDbConnector (@Named("databaseOfAccount")String databaseName, Context
            ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("personCouchDbConnector")
    TouchDBHelper getPersonStdCouchDbConnector (@Named("databaseOfPerson")String
                                                           databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("boatCouchDbConnector")
    TouchDBHelper getBoatStdCouchDbConnector (@Named("databaseOfBoat")String
                                                         databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("tripCouchDbConnector")
    TouchDBHelper getTripStdCouchDbConnector (@Named("databaseOfTrip")String
                                                         databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("waypointCouchDbConnector")
    TouchDBHelper getWaypointStdCouchDbConnector (@Named("databaseOfWaypoint")String
                                                             databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("routeCouchDbConnector")
    TouchDBHelper getRouteStdCouchDbConnector (@Named("databaseOfRoute")String
                                                          databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("markCouchDbConnector")
    TouchDBHelper getMarkStdCouchDbConnector (@Named("databaseOfMark")String
                                                         databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }

    @Provides
    @Named("raceCouchDbConnector")
    TouchDBHelper getRaceStdCouchDbConnector (@Named("databaseOfRace")String
                                                         databaseName, Context ctx){
        return new TouchDBHelper(databaseName, ctx);
    }


}