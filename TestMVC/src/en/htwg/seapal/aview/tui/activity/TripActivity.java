package en.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.os.Bundle;
import de.htwg.seapal.R;
import en.htwg.seapal.aview.tui.states.trip.StartState;
import en.htwg.seapal.controller.ITripController;
import en.htwg.seapal.controller.impl.TripController;
import en.htwg.seapal.observer.IObserver;

public class TripActivity extends AActivity implements IObserver {

	private ITripController controller;
	private UUID boat;

	@Override
	public void setup() {
		setContentView(R.layout.trip);
		Bundle bundle = getIntent().getExtras();
		setBoat(UUID.fromString(bundle.getString("boat").toString()));
//		setController(new TripController(new ListTripDatabase()));
		currenState = new StartState();
//		controller.addObserver(this);
	}

	public ITripController getController() {
		return controller;
	}

	public void setController(ITripController controller) {
		this.controller = controller;
	}

	public UUID getBoat() {
		return boat;
	}

	public void setBoat(UUID boat) {
		this.boat = boat;
	}

}
