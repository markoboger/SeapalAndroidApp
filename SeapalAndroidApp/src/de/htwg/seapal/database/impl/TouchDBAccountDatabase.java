package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.ModelDocument;

/**
 * Created by jakub on 2/15/14.
 */
public class TouchDBAccountDatabase implements IAccountDatabase{
    @Override
    public IAccount getAccount(String s) {
        return null;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public UUID create() {
        return null;
    }

    @Override
    public boolean save(IAccount iAccount) {
        return false;
    }

    @Override
    public IAccount get(UUID uuid) {
        return null;
    }

    @Override
    public List<IAccount> loadAll() {
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
    public List<? extends IAccount> queryViews(String s, String s2) {
        return null;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
