package de.htwg.seapal.aview.gui.plugins;

/**
 * Created by jakub on 12/28/13.
 *
 * interface for a Map plugin able class
 *
 * usually it it used with a map of Map<String, IMapPlugin>
 *
 *
 */
public interface IMapPluginable {

    /**
     * used to register a GoogleMap plugin.
     * @param name the name of the plugin in the map
     * @param plugin the new instance of the plugin
     */
    void registerMapPlugin(String name, IMapPlugin plugin);

    /**
     *
     * used to unregister a plugin
     * @param name  the name of the plugin in the map
     */
    void unregisterMapPlugin(String name);

    /**
     * you can get the instance of the IMapPlugin through the registered name.
     * @param name the name of the plugin in the map
     * @return the instance of the IMapPlugin
     */
    IMapPlugin getMapPlugin(String name);
}
