package nl.hu.bep.group4.bifi.lader.factories;

import nl.hu.bep.group4.bifi.interfaces.FactuurLader;
import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.KlantLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MongoLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.lader.implementations.AdresLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.FactuurLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.KlantLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.LegacyJarLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.MongoLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.PersoonLaderImpl;


public class FactuurLaderFactory {

	public static FactuurLader createFactuurLader() {

		MysqlLader mysqlLader = new MysqlLaderImpl();
		MongoLader mongoLader = new MongoLaderImpl();
		PersoonLader persoonLader = new PersoonLaderImpl(mysqlLader);
		LegacyJarLader legacyJarLader = new LegacyJarLaderImpl();
		AdresLader adresLader = new AdresLaderImpl(legacyJarLader, mysqlLader);

		KlantLader klantLader = new KlantLaderImpl(adresLader, persoonLader, mysqlLader);

		return new FactuurLaderImpl(klantLader, persoonLader, mongoLader);
	}

	private FactuurLaderFactory() {}
}
