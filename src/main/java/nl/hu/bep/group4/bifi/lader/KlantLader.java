package nl.hu.bep.group4.bifi.lader;

import java.io.IOException;
import java.sql.SQLException;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.model.Klant;

public interface KlantLader {
	Klant getKlant(int klantId) throws SQLException, IOException, ClassNotFoundException, GarbageDataException;
}
