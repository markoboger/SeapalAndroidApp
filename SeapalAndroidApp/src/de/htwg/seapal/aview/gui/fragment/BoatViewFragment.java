package de.htwg.seapal.aview.gui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.model.IBoat;
import roboguice.RoboGuice;


/**
 * Created by jakub on 11/16/13.
 */
public class BoatViewFragment extends Fragment {
    public final static String ARG_POSITION = "position";
    public final static String ARG_UUID = "uuid";
    private int mCurrentPosition = -1;
    private UUID mCurrentUUID;
    @Inject
    private BoatController boatController;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            mCurrentUUID = (UUID) savedInstanceState.get(ARG_UUID);
        }




        return inflater.inflate(R.layout.boat_view, container, false);


    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateBoatView(args.getInt(ARG_POSITION), (UUID) args.get(ARG_UUID));
        } else {
            updateBoatView(mCurrentPosition, mCurrentUUID);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateBoatView(int position, UUID uuid) {
        name = (EditText) getActivity().findViewById(R.id.editBoatName);
        typ = (EditText) getActivity().findViewById(R.id.editType);
        productionYear = (EditText) getActivity().findViewById( R.id.editProductionYear);
        corporateIdNumber = (EditText) getActivity().findViewById( R.id.editIdNumber);
        draftsman = (EditText) getActivity().findViewById(R.id.editDraftsman);
        engine = (EditText) getActivity().findViewById(R.id.editEngine);
        sailEmblem = (EditText) getActivity().findViewById(R.id.editSailEmblem);
        length = (EditText) getActivity().findViewById(R.id.editLength);
        tankSize = (EditText) getActivity().findViewById(R.id.editTankSize);
        homePort = (EditText) getActivity().findViewById(R.id.editHomePort);
        width = (EditText) getActivity().findViewById(R.id.editWidth);
        waterTankSize = (EditText) getActivity().findViewById(R.id.editWaterTankSize);
        yachtClub = (EditText) getActivity().findViewById(R.id.editYachtClub);
        flotationDepth = (EditText) getActivity().findViewById( R.id.editFlotationDepth);
        sewageWaterTankSize = (EditText) getActivity().findViewById( R.id.editSewageWaterTankSize);
        owner = (EditText) getActivity().findViewById(R.id.editOwner);
        mastHeight = (EditText) getActivity().findViewById(R.id.editMastHeight);
        mainSailSize = (EditText) getActivity().findViewById( R.id.editMainSailSize);
        insurance = (EditText) getActivity().findViewById(R.id.editInsurance);
        displacement = (EditText) getActivity().findViewById( R.id.editDisplacement);
        genoaSize = (EditText) getActivity().findViewById(R.id.editGenoaSize);
        callSign = (EditText) getActivity().findViewById(R.id.editCallSign);
        rigKind = (EditText) getActivity().findViewById(R.id.editRigKind);
        spiSize = (EditText) getActivity().findViewById(R.id.editSpiSize);
        if (uuid != null) {
            IBoat b = boatController.getBoat(uuid);
            if (b != null) {


                name.setText(b.getBoatName());
                typ.setText(b.getType());
                productionYear.setText(Integer.toString(b .getYearOfConstruction()));
                corporateIdNumber.setText(b.getRegisterNr());
                draftsman.setText(b.getConstructor());
                engine.setText(b.getMotor());
                sailEmblem.setText(b.getSailSign());
                length.setText(Double.toString(b.getLength()));
                tankSize.setText(Double.toString(b.getTankSize()));
                homePort.setText(b.getHomePort());
                width.setText(Double.toString(b.getWidth()));
                waterTankSize.setText(Double.toString(b
                        .getWasteWaterTankSize()));
                yachtClub.setText(b.getYachtclub());
                flotationDepth.setText(Double.toString(b.getDraft()));
                sewageWaterTankSize.setText(Double.toString(b
                        .getWasteWaterTankSize()));
                if (b.getOwner() != null)
                    owner.setText(b.getOwner().toString());
                mastHeight.setText(Double.toString(b.getMastHeight()));
                mainSailSize.setText(Double.toString(b.getMainSailSize()));
                insurance.setText(b.getInsurance());
                displacement.setText(Double.toString(b.getDisplacement()));
                genoaSize.setText(Double.toString(b.getGenuaSize()));
                callSign.setText(b.getCallSign());
                rigKind.setText(b.getRigging());
                spiSize.setText(Double.toString(b.getSpiSize()));

                mCurrentPosition = position;
                mCurrentUUID = uuid;
            }
        }
    }
}
