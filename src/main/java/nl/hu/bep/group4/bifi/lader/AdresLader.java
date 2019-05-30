package nl.hu.bep.group4.bifi.lader;

import java.util.List;

import nl.hu.bep.group4.bifi.model.Adres;

public interface AdresLader {
	public List<Adres> getAdres(int klantId);
}
