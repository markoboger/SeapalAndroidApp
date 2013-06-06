package de.htwg.seapal.aview.gui.fragment;

import java.util.UUID;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.BoatController;

public class BoatDetailFragment extends Fragment {

	public static String TAG = "FragmentDetail";
	private UUID boat;
	private BoatController controller;

	private EditText name;
	private EditText typ;
	private EditText productionYear;
	private EditText corporateIdNumber;
	private EditText draftsman;
	private EditText engine;
	private EditText sailEmblem;
	private EditText length;
	private EditText tankSize;
	private EditText homePort;
	private EditText width;
	private EditText waterTankSize;
	private EditText yachtClub;
	private EditText flotationDepth;
	private EditText sewageWaterTankSize;
	private EditText owner;
	private EditText mastHeight;
	private EditText mainSailSize;
	private EditText insurance;
	private EditText displacement;
	private EditText genoaSize;
	private EditText callSign;
	private EditText rigKind;
	private EditText spiSize;

	public BoatDetailFragment() {
	}

	public void setBoat(UUID boat) {
		this.boat = boat;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		name = (EditText) getActivity().findViewById(R.id.editBoatName);
		typ = (EditText) getActivity().findViewById(R.id.editType);
		productionYear = (EditText) getActivity().findViewById(
				R.id.editProductionYear);
		productionYear.setInputType(InputType.TYPE_CLASS_NUMBER);
		corporateIdNumber = (EditText) getActivity().findViewById(
				R.id.editIdNumber);
		draftsman = (EditText) getActivity().findViewById(R.id.editDraftsman);
		engine = (EditText) getActivity().findViewById(R.id.editEngine);
		sailEmblem = (EditText) getActivity().findViewById(R.id.editSailEmblem);
		length = (EditText) getActivity().findViewById(R.id.editLength);
		length.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		tankSize = (EditText) getActivity().findViewById(R.id.editTankSize);
		tankSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		homePort = (EditText) getActivity().findViewById(R.id.editHomePort);
		width = (EditText) getActivity().findViewById(R.id.editWidth);
		width.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		waterTankSize = (EditText) getActivity().findViewById(
				R.id.editWaterTankSize);
		waterTankSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		yachtClub = (EditText) getActivity().findViewById(R.id.editYachtClub);
		flotationDepth = (EditText) getActivity().findViewById(
				R.id.editFlotationDepth);
		flotationDepth.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		sewageWaterTankSize = (EditText) getActivity().findViewById(
				R.id.editSewageWaterTankSize);
		sewageWaterTankSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		owner = (EditText) getActivity().findViewById(R.id.editOwner);
		mastHeight = (EditText) getActivity().findViewById(R.id.editMastHeight);
		mastHeight.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		mainSailSize = (EditText) getActivity().findViewById(
				R.id.editMainSailSize);
		mainSailSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		insurance = (EditText) getActivity().findViewById(R.id.editInsurance);
		displacement = (EditText) getActivity().findViewById(
				R.id.editDisplacement);
		displacement.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		genoaSize = (EditText) getActivity().findViewById(R.id.editGenoaSize);
		genoaSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
		callSign = (EditText) getActivity().findViewById(R.id.editCallSign);
		rigKind = (EditText) getActivity().findViewById(R.id.editRigKind);
		spiSize = (EditText) getActivity().findViewById(R.id.editSpiSize);
		spiSize.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View newView = inflater.inflate(R.layout.boatdetails, null);
		ViewGroup rootView = (ViewGroup) getView();
		rootView.removeAllViews();
		rootView.addView(newView);
		onActivityCreated(null);
		onResume();
	}

	@Override
	public void onResume() {
		if (boat != null) {
			refresh(boat);
		}
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.boatdetails, container, false);
	}

	public void refresh(UUID boat) {
		this.boat = boat;

		name.setText(controller.getBoatName(boat));
		typ.setText(controller.getType(boat));
		productionYear.setText(Integer.toString(controller
				.getYearOfConstruction(boat)));
		corporateIdNumber.setText(controller.getRegisterNr(boat));
		draftsman.setText(controller.getConstructor(boat));
		engine.setText(controller.getMotor(boat));
		sailEmblem.setText(controller.getSailSign(boat));
		length.setText(Double.toString(controller.getLength(boat)));
		tankSize.setText(Double.toString(controller.getTankSize(boat)));
		homePort.setText(controller.getHomePort(boat));
		width.setText(Double.toString(controller.getWidth(boat)));
		waterTankSize.setText(Double.toString(controller
				.getWasteWaterTankSize(boat)));
		yachtClub.setText(controller.getYachtclub(boat));
		flotationDepth.setText(Double.toString(controller.getDraft(boat)));
		sewageWaterTankSize.setText(Double.toString(controller
				.getWasteWaterTankSize(boat)));
		if (controller.getOwner(boat) != null)
			owner.setText(controller.getOwner(boat).toString());
		mastHeight.setText(Double.toString(controller.getMastHeight(boat)));
		mainSailSize.setText(Double.toString(controller.getMainSailSize(boat)));
		insurance.setText(controller.getInsurance(boat));
		displacement.setText(Double.toString(controller.getDisplacement(boat)));
		genoaSize.setText(Double.toString(controller.getGenuaSize(boat)));
		callSign.setText(controller.getCallSign(boat));
		rigKind.setText(controller.getRigging(boat));
		spiSize.setText(Double.toString(controller.getSpiSize(boat)));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar, menu);
		MenuItem itemNew = menu.findItem(R.id.menu_new);
		itemNew.setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_delete:
			deleteBoat();
			break;
		case R.id.menu_save:
			saveBoat();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteBoat() {
		if (boat == null) {
			Toast.makeText(getActivity(), "Please select a Boat",
					Toast.LENGTH_SHORT).show();
			return;
		}
		new AlertDialog.Builder(getActivity())
				.setTitle("Delete Boat")
				.setMessage(
						"Do you really want to delete the Boat: " + "'"
								+ controller.getBoatName(boat) + "'")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								controller.deleteBoat(boat);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;

					}
				}).show();

	}

	private void saveBoat() {
		if (boat == null) {
			Toast.makeText(getActivity(), "Please select a Boat",
					Toast.LENGTH_SHORT).show();
			return;
		}
		try {

			if (!controller.getBoatName(boat).equals(name.getText().toString()))
				controller.setBoatName(boat, name.getText().toString());
			if (!controller.getType(boat).equals(typ.getText().toString()))
				controller.setType(boat, typ.getText().toString());
			if (!controller.getRegisterNr(boat).equals(
					corporateIdNumber.getText().toString()))
				controller.setRegisterNr(boat, corporateIdNumber.getText()
						.toString());
			if (!controller.getConstructor(boat).equals(
					draftsman.getText().toString()))
				controller.setConstructor(boat, draftsman.getText().toString());
			if (!controller.getMotor(boat).equals(engine.getText().toString()))
				controller.setMotor(boat, engine.getText().toString());
			if (!controller.getSailSign(boat).equals(
					sailEmblem.getText().toString()))
				controller.setSailSign(boat, sailEmblem.getText().toString());
			if (!controller.getHomePort(boat).equals(
					homePort.getText().toString()))
				controller.setHomePort(boat, homePort.getText().toString());
			if (!controller.getInsurance(boat).equals(
					insurance.getText().toString()))
				controller.setInsurance(boat, insurance.getText().toString());
			if (!controller.getCallSign(boat).equals(
					callSign.getText().toString()))
				controller.setCallSign(boat, callSign.getText().toString());
			if (!controller.getRigging(boat).equals(
					rigKind.getText().toString()))
				controller.setRigging(boat, rigKind.getText().toString());
			// if(!controller.getOwner(boat).equals(owner.getText().toString()))
			// controller.setOwner(boat,
			// UUID.fromString(owner.getText().toString()));
			if (controller.getYearOfConstruction(boat) != Integer
					.valueOf(productionYear.getText().toString()))
				controller.setYearOfConstruction(boat,
						Integer.valueOf(productionYear.getText().toString()));
			if (controller.getLength(boat) != Double.valueOf(length.getText()
					.toString()))
				controller.setLength(boat,
						Double.valueOf(length.getText().toString()));
			if (controller.getTankSize(boat) != Double.valueOf(tankSize
					.getText().toString()))
				controller.setTankSize(boat,
						Double.valueOf(tankSize.getText().toString()));
			if (controller.getWidth(boat) != Double.valueOf(width.getText()
					.toString()))
				controller.setWidth(boat,
						Double.valueOf(width.getText().toString()));
			if (controller.getFreshWaterTankSize(boat) != Double
					.valueOf(waterTankSize.getText().toString()))
				controller.setFreshWaterTankSize(boat,
						Double.valueOf(waterTankSize.getText().toString()));
			if (controller.getDraft(boat) != Double.valueOf(flotationDepth
					.getText().toString()))
				controller.setDraft(boat,
						Double.valueOf(flotationDepth.getText().toString()));
			if (controller.getWasteWaterTankSize(boat) != Double
					.valueOf(sewageWaterTankSize.getText().toString()))
				controller.setWasteWaterTankSize(boat, Double
						.valueOf(sewageWaterTankSize.getText().toString()));
			if (controller.getMastHeight(boat) != Double.valueOf(mastHeight
					.getText().toString()))
				controller.setMastHeight(boat,
						Double.valueOf(mastHeight.getText().toString()));
			if (controller.getMainSailSize(boat) != Double.valueOf(mainSailSize
					.getText().toString()))
				controller.setMainSailSize(boat,
						Double.valueOf(mainSailSize.getText().toString()));
			if (controller.getDisplacement(boat) != Double.valueOf(displacement
					.getText().toString()))
				controller.setDisplacement(boat,
						Double.valueOf(displacement.getText().toString()));
			if (controller.getGenuaSize(boat) != Double.valueOf(genoaSize
					.getText().toString()))
				controller.setGenuaSize(boat,
						Double.valueOf(genoaSize.getText().toString()));
			if (controller.getSpiSize(boat) != Double.valueOf(spiSize.getText()
					.toString()))
				controller.setSpiSize(boat,
						Double.valueOf(spiSize.getText().toString()));
		} catch (NumberFormatException e) {

		}
		Toast.makeText(getActivity(), "Saved Changes", Toast.LENGTH_SHORT)
				.show();

	}

	public void clear() {
		name.setText("");
		typ.setText("");
		productionYear.setText(Integer.toString(controller
				.getYearOfConstruction(boat)));
		corporateIdNumber.setText(controller.getRegisterNr(boat));
		draftsman.setText(controller.getConstructor(boat));
		engine.setText(controller.getMotor(boat));
		sailEmblem.setText(controller.getSailSign(boat));
		length.setText(Double.toString(controller.getLength(boat)));
		tankSize.setText(Double.toString(controller.getTankSize(boat)));
		homePort.setText(controller.getHomePort(boat));
		width.setText(Double.toString(controller.getWidth(boat)));
		waterTankSize.setText(Double.toString(controller
				.getWasteWaterTankSize(boat)));
		yachtClub.setText(controller.getYachtclub(boat));
		flotationDepth.setText(Double.toString(controller.getDraft(boat)));
		sewageWaterTankSize.setText(Double.toString(controller
				.getWasteWaterTankSize(boat)));
		if (controller.getOwner(boat) != null)
			owner.setText(controller.getOwner(boat).toString());
		mastHeight.setText(Double.toString(controller.getMastHeight(boat)));
		mainSailSize.setText(Double.toString(controller.getMainSailSize(boat)));
		insurance.setText(controller.getInsurance(boat));
		displacement.setText(Double.toString(controller.getDisplacement(boat)));
		genoaSize.setText(Double.toString(controller.getGenuaSize(boat)));
		callSign.setText(controller.getCallSign(boat));
		rigKind.setText(controller.getRigging(boat));
		spiSize.setText(Double.toString(controller.getSpiSize(boat)));
	}

	public void setController(BoatController controller) {
		this.controller = controller;
	}

}
