package nl.hu.bep.group4.bifi.lader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public interface MysqlLader {
	List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException, IOException;
	Klant getKlant(int klantId) throws SQLException, ClassNotFoundException, IOException;
	List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException;
	Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException;
	Adres getFactuurAdres(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException;
}
