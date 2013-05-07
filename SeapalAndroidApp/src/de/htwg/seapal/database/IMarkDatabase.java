package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IMark;

public interface IMarkDatabase {

	UUID newMark();

	void saveMark(IMark mark);

	void deleteMark(UUID id);

	IMark getMark(UUID id);

	List<IMark> getMarks();

	boolean closeDB();
}
