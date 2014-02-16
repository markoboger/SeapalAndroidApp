package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IRaceDatabase;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model._IRace;

/**
 * Created by jakub on 2/15/14.
 */
public class TouchDBRaceDatabase implements IRaceDatabase{
    @Override
    public boolean open() {
        return false;
    }

    @Override
    public UUID create() {
        return null;
    }

    @Override
    public boolean save(_IRace iRace) {
        return false;
    }

    @Override
    public _IRace get(UUID uuid) {
        return null;
    }

    @Override
    public List<_IRace> loadAll() {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends _IRace> queryViews(String s, String s2) {
        return null;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
