package nl.hu.bep.group4.bifi.lader.factories;

import nl.hu.bep.group4.bifi.interfaces.FactuurLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.implementations.*;

public class FactuurLaderFactory {

	public static FactuurLader createFactuurLader() {

		MysqlLader mysqlLader = new MysqlLaderImpl();
		MongoLaderImpl mongoLader = new MongoLaderImpl();
		PersoonLaderImpl persoonLader = new PersoonLaderImpl(mysqlLader);
		LegacyJarLader legacyJarLader = new LegacyJarLaderImpl();
		AdresLaderImpl adresLader = new AdresLaderImpl(legacyJarLader, mysqlLader);

		KlantLaderImpl klantLader = new KlantLaderImpl(adresLader, persoonLader, mysqlLader);

		FactuurLaderImpl factuurLader = new FactuurLaderImpl(klantLader, persoonLader, mongoLader);

		return factuurLader;
	}
	private FactuurLaderFactory() {}
}
