package de.htwg.seapal.database.impl;

import android.content.Context;

import com.google.inject.Inject;

import de.htwg.seapal.database.TouchDBHelper;


public class TouchDBHelperReplication extends TouchDBHelper{


    @Inject
    public TouchDBHelperReplication(String dbName, Context ctx) {
        super(dbName, ctx);
        transitionToReplicationHelper();
    }





}
