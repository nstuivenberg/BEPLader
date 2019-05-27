package nl.hu.bep.group4.bifi.lader;

import java.util.List;

import nl.hu.bep.group4.bifi.model.Persoon;

public interface PersoonLader {
	public List<Persoon> getPersoon(int klantId);
}
