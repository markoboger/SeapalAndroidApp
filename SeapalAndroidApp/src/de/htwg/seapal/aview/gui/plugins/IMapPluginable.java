package de.htwg.seapal.aview.gui.plugins;

/**
 * Created by jakub on 12/28/13.
 */
public interface IMapPluginable {

    void registerMapPlugin(String name, IMapPlugin plugin);
    void unregisterMapPlugin(String name);
    IMapPlugin getMapPlugin(String name);
}
