package nl.hu.bep.group4.bifi.lader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.model.Persoon;

public interface PersoonLader {
	List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException;
	Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException;
}
