package nl.hu.bep.group4.bifi.lader;

import java.util.List;

import nl.hu.bep.group4.bifi.interfaces.FactuurLader;
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
	public List<Factuur> getFacturenVoorMaand(int maandNummer) {
		// TODO Auto-generated method stub
		return null;
	}

}
