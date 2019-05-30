package nl.hu.bep.group4.bifi.lader;

import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.model.Factuur;

public interface MongoLader {
	public List<Factuur> getFacturenVoorMaand(int maandNummer) throws GarbageDataException;
}
