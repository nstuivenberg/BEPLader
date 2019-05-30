package nl.hu.bep.group4.bifi.lader;

import java.sql.SQLException;
import java.util.List;

import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public interface MysqlLader {
	List<Adres> getAdres(int klantId) throws SQLException;
	Klant getKlant(int klantId) throws SQLException;
	List<Persoon> getPersoon(int klantId) throws SQLException;
	Adres getFactuurAdres(int klantId) throws SQLException;
}
