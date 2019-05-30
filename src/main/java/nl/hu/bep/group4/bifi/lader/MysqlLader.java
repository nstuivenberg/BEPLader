package nl.hu.bep.group4.bifi.lader;

import java.util.List;

import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public interface MysqlLader {
	public List<Adres> getAdres(int klantId);
	public Klant getKlant(int klantId);
	public List<Persoon> getPersoon(int klantId);
}
