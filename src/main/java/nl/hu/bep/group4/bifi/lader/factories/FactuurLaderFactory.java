package nl.hu.bep.group4.bifi.lader.factories;

import nl.hu.bep.group4.bifi.interfaces.FactuurLader;
import nl.hu.bep.group4.bifi.lader.implementations.FactuurLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.KlantLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.MongoLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.PersoonLaderImpl;

public class FactuurLaderFactory {
	public static FactuurLader createFactuurLader() {
		return new FactuurLaderImpl(new KlantLaderImpl(), new PersoonLaderImpl(), new MongoLaderImpl());
	}
	private FactuurLaderFactory() {}
}
