package nl.hu.bep.group4.bifi.lader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.model.Adres;

public interface AdresLader {
	List<Adres> getAdressen(int klantId) throws SQLException, IOException, ClassNotFoundException;
	Adres getFactuurAdres(int klantId) throws GarbageDataException, SQLException, ClassNotFoundException, IOException;
}
