package de.htwg.seapal.aview.gui.fragment;

import java.util.UUID;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.BoatController;

public class FragmentDetail extends Fragment {

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

	public FragmentDetail() {
		
	}

	public void setBoat(UUID boat) {
		this.boat = boat;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		name = (EditText) getActivity().findViewById(R.id.editBoatName);
		typ = (EditText) getActivity().findViewById(R.id.editType);
		productionYear = (EditText) getActivity().findViewById(
				R.id.editProductionYear);
		corporateIdNumber = (EditText) getActivity().findViewById(
				R.id.editIdNumber);
		draftsman = (EditText) getActivity().findViewById(R.id.editDraftsman);
		engine = (EditText) getActivity().findViewById(R.id.editEngine);
		sailEmblem = (EditText) getActivity().findViewById(R.id.editSailEmblem);
		length = (EditText) getActivity().findViewById(R.id.editLength);
		tankSize = (EditText) getActivity().findViewById(R.id.editTankSize);
		homePort = (EditText) getActivity().findViewById(R.id.editHomePort);
		width = (EditText) getActivity().findViewById(R.id.editWidth);
		waterTankSize = (EditText) getActivity().findViewById(
				R.id.editWaterTankSize);
		yachtClub = (EditText) getActivity().findViewById(R.id.editYachtClub);
		flotationDepth = (EditText) getActivity().findViewById(
				R.id.editFlotationDepth);
		sewageWaterTankSize = (EditText) getActivity().findViewById(
				R.id.editSewageWaterTankSize);
		owner = (EditText) getActivity().findViewById(R.id.editOwner);
		mastHeight = (EditText) getActivity().findViewById(R.id.editMastHeight);
		mainSailSize = (EditText) getActivity().findViewById(
				R.id.editMainSailSize);
		insurance = (EditText) getActivity().findViewById(R.id.editInsurance);
		displacement = (EditText) getActivity().findViewById(
				R.id.editDisplacement);
		genoaSize = (EditText) getActivity().findViewById(R.id.editGenoaSize);
		callSign = (EditText) getActivity().findViewById(R.id.editCallSign);
		rigKind = (EditText) getActivity().findViewById(R.id.editRigKind);
		spiSize = (EditText) getActivity().findViewById(R.id.editSpiSize);
		
	}
	
	
	@Override
	public void onResume() {
		if (boat != null) {
			refresh(boat);
		}
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
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
		productionYear.setText(Integer.toString(controller.getYearOfConstruction(boat)));
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
		if(controller.getOwner(boat) != null)
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
