package de.htwg.seapal.aview.gui.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.Manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.model.impl.Waypoint;
import roboguice.RoboGuice;

public class WaypointListAdapter extends ArrayAdapter<UUID> {

    private final List<UUID> waypoints;

    @Inject
    private IMainController controller;

    @Inject
    private SessionManager sessionManager;

    public WaypointListAdapter(Context context, List<UUID> items) {
        super(context, R.layout.tripwaypointlistadapter, items);
        this.waypoints = items;
        Injector i = RoboGuice.getInjector(getContext());
        i.injectMembers(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.tripwaypointlistadapter, null);
        }

        UUID waypoint = waypoints.get(position);

        if (waypoint != null) {
            Collection<Waypoint> waypoints1 = (Collection<Waypoint>) controller.getSingleDocument("waypoint", sessionManager.getSession(), waypoint);
            if (waypoints1.iterator().hasNext()) {
                Waypoint w = waypoints1.iterator().next();

                if (w != null) {
                    TextView tt0 = (TextView) v
                            .findViewById(R.id.tripwaypointlistadapter_name);
                    TextView tt2 = (TextView) v
                            .findViewById(R.id.tripwaypointlistadapter_cog);
                    TextView tt3 = (TextView) v
                            .findViewById(R.id.tripwaypointlistadapter_sog);
                    TextView tt4 = (TextView) v
                            .findViewById(R.id.tripwaypointlistadapter_lat);
                    TextView tt5 = (TextView) v
                            .findViewById(R.id.tripwaypointlistadapter_long);

                    if (tt0 != null)
                        tt0.setText(w.getName());

                    if (tt2 != null)
                        tt2.setText(w.getCOG());

                    if (tt3 != null)
                        tt3.setText(w.getSOG());

                    if (tt4 != null) {
                        Location location = new Location("");
                        location.setLatitude(w.getLatitude());
                        tt4.setText(Double.toString(location.getLatitude()));
                    }

                    if (tt5 != null) {
                        Location location = new Location("");
                        location.setLongitude(w.getLongitude());
                        tt5.setText(Double.toString(location.getLongitude()));
                    }

                }
            }


        }

        return v;
    }

}
