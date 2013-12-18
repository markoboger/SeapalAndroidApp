package de.htwg.seapal.utils.seapal;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import de.htwg.seapal.R;
import de.htwg.seapal.model.IBoat;

/**
 * Created by jakub on 11/18/13.
 */
public class BoatUtils {

    public static void  setViewFromBoat(Activity view, IBoat boat) {
        EditText name = (EditText) view.findViewById(R.id.editBoatName);
        EditText typ = (EditText) view.findViewById(R.id.editType);
        EditText productionYear = (EditText) view.findViewById(R.id.editProductionYear);
        EditText corporateIdNumber = (EditText) view.findViewById(R.id.editIdNumber);
        EditText draftsman = (EditText) view.findViewById(R.id.editDraftsman);
        EditText engine = (EditText) view.findViewById(R.id.editEngine);
        EditText sailEmblem = (EditText) view.findViewById(R.id.editSailEmblem);
        EditText length = (EditText) view.findViewById(R.id.editLength);
        EditText tankSize = (EditText) view.findViewById(R.id.editTankSize);
        EditText homePort = (EditText) view.findViewById(R.id.editHomePort);
        EditText width = (EditText) view.findViewById(R.id.editWidth);
        EditText waterTankSize = (EditText) view.findViewById(R.id.editWaterTankSize);
        EditText yachtClub = (EditText) view.findViewById(R.id.editYachtClub);
        EditText flotationDepth = (EditText) view.findViewById(R.id.editFlotationDepth);
        EditText sewageWaterTankSize = (EditText) view.findViewById(R.id.editSewageWaterTankSize);
        EditText owner = (EditText) view.findViewById(R.id.editOwner);
        EditText mastHeight = (EditText) view.findViewById(R.id.editMastHeight);
        EditText mainSailSize = (EditText) view.findViewById(R.id.editMainSailSize);
        EditText insurance = (EditText) view.findViewById(R.id.editInsurance);
        EditText displacement = (EditText) view.findViewById(R.id.editDisplacement);
        EditText genoaSize = (EditText) view.findViewById(R.id.editGenoaSize);
        EditText callSign = (EditText) view.findViewById(R.id.editCallSign);
        EditText rigKind = (EditText) view.findViewById(R.id.editRigKind);
        EditText spiSize = (EditText) view.findViewById(R.id.editSpiSize);

        name.setText(boat.getBoatName());
        typ.setText(boat.getType());
        productionYear.setText(Integer.toString(boat.getYearOfConstruction()));
        corporateIdNumber.setText(boat.getRegisterNr());
        draftsman.setText(boat.getConstructor());
        engine.setText(boat.getMotor());
        sailEmblem.setText(boat.getSailSign());
        length.setText(Double.toString(boat.getLength()));
        tankSize.setText(Double.toString(boat.getTankSize()));
        homePort.setText(boat.getHomePort());
        width.setText(Double.toString(boat.getWidth()));
        waterTankSize.setText(Double.toString(boat.getWasteWaterTankSize()));
        yachtClub.setText(boat.getYachtclub());
        flotationDepth.setText(Double.toString(boat.getDraft()));
        sewageWaterTankSize.setText(Double.toString(boat.getWasteWaterTankSize()));
        if (boat.getOwner() != null)
            owner.setText(boat.getOwner());
        mastHeight.setText(Double.toString(boat.getMastHeight()));
        mainSailSize.setText(Double.toString(boat.getMainSailSize()));
        insurance.setText(boat.getInsurance());
        displacement.setText(Double.toString(boat.getDisplacement()));
        genoaSize.setText(Double.toString(boat.getGenuaSize()));
        callSign.setText(boat.getCallSign());
        rigKind.setText(boat.getRigging());
        spiSize.setText(Double.toString(boat.getSpiSize()));
    }

    public static IBoat convertViewToBoat(View view, IBoat boat) {
        EditText name = (EditText) view.findViewById(R.id.editBoatName);
        EditText typ = (EditText) view.findViewById(R.id.editType);
        EditText productionYear = (EditText) view.findViewById(R.id.editProductionYear);
        EditText corporateIdNumber = (EditText) view.findViewById(R.id.editIdNumber);
        EditText draftsman = (EditText) view.findViewById(R.id.editDraftsman);
        EditText engine = (EditText) view.findViewById(R.id.editEngine);
        EditText sailEmblem = (EditText) view.findViewById(R.id.editSailEmblem);
        EditText length = (EditText) view.findViewById(R.id.editLength);
        EditText tankSize = (EditText) view.findViewById(R.id.editTankSize);
        EditText homePort = (EditText) view.findViewById(R.id.editHomePort);
        EditText width = (EditText) view.findViewById(R.id.editWidth);
        EditText waterTankSize = (EditText) view.findViewById(R.id.editWaterTankSize);
        EditText yachtClub = (EditText) view.findViewById(R.id.editYachtClub);
        EditText flotationDepth = (EditText) view.findViewById(R.id.editFlotationDepth);
        EditText sewageWaterTankSize = (EditText) view.findViewById(R.id.editSewageWaterTankSize);
        EditText owner = (EditText) view.findViewById(R.id.editOwner);
        EditText mastHeight = (EditText) view.findViewById(R.id.editMastHeight);
        EditText mainSailSize = (EditText) view.findViewById(R.id.editMainSailSize);
        EditText insurance = (EditText) view.findViewById(R.id.editInsurance);
        EditText displacement = (EditText) view.findViewById(R.id.editDisplacement);
        EditText genoaSize = (EditText) view.findViewById(R.id.editGenoaSize);
        EditText callSign = (EditText) view.findViewById(R.id.editCallSign);
        EditText rigKind = (EditText) view.findViewById(R.id.editRigKind);
        EditText spiSize = (EditText) view.findViewById(R.id.editSpiSize);

        boat.setBoatName(name.getText().toString());
        boat.setType(typ.getText().toString());
        boat.setRegisterNr(corporateIdNumber.getText() .toString());
        boat.setConstructor(draftsman.getText().toString());
        boat.setMotor(engine.getText().toString());
        boat.setSailSign(sailEmblem.getText().toString());
        boat.setHomePort(homePort.getText().toString());
        boat.setInsurance(insurance.getText().toString());
        boat.setCallSign(callSign.getText().toString());
        boat.setRigging(rigKind.getText().toString());
        boat.setYearOfConstruction(Integer.valueOf(productionYear.getText().toString()));
        boat.setLength(Double.valueOf(length.getText().toString()));
        boat.setTankSize( Double.valueOf(tankSize.getText().toString()));
        boat.setWidth(Double.valueOf(width.getText().toString()));
        boat.setFreshWaterTankSize(Double.valueOf(waterTankSize.getText().toString()));
        boat.setDraft(Double.valueOf(flotationDepth.getText().toString()));
        boat.setWasteWaterTankSize(Double.valueOf(sewageWaterTankSize.getText().toString()));
        boat.setMastHeight(Double.valueOf(mastHeight.getText().toString()));
        boat.setMainSailSize(Double.valueOf(mainSailSize.getText().toString()));
        boat.setDisplacement(Double.valueOf(displacement.getText().toString()));
        boat.setGenuaSize(Double.valueOf(genoaSize.getText().toString()));
        boat.setSpiSize(Double.valueOf(spiSize.getText().toString()));
        return boat;

    }
}
