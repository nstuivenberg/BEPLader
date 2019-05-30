package nl.hu.bep.group4.bifi.lader.implementations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.interfaces.FactuurLader;
import nl.hu.bep.group4.bifi.lader.KlantLader;
import nl.hu.bep.group4.bifi.lader.MongoLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.model.Factuur;

public class FactuurLaderImpl implements FactuurLader {
	private KlantLader klantLader;
	private PersoonLader persoonLader;
	private MongoLader mongoLader;
	
	public FactuurLaderImpl(KlantLader klantLader, PersoonLader persoonLader, MongoLader mongoLader) {
		this.klantLader = klantLader;
		this.persoonLader = persoonLader;
		this.mongoLader = mongoLader;
	}

	@Override
	public List<Factuur> getFacturenVoorMaand(int maandNummer) throws GarbageDataException, ClassNotFoundException, SQLException, IOException {
		this.persoonLader.getPersoon(1);
		List<Factuur> facturen = this.mongoLader.getFacturenVoorMaand(1);
		for(Factuur factuur : facturen) {
			factuur.setKlant(this.klantLader.getKlant(factuur.getKlant().getId()));
			this.persoonLader.getPersoon(factuur.getKlant().getId());
		}
		return facturen;
	}

}
