package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.*;
import nl.hu.bep.group4.bifi.model.Klant;

public class KlantLaderImpl implements KlantLader {
    private AdresLader adresLader;
    private PersoonLader persoonLader;
    private MysqlLader mysqlLader;

    public KlantLaderImpl(AdresLader adresLader, PersoonLader persoonLader, MysqlLader mysqlLader) {
        this.adresLader = adresLader;
        this.persoonLader = persoonLader;
        this.mysqlLader = mysqlLader;
    }

	@Override
	public Klant getKlant(int klantId) {

        Klant klantFromDatabase = mysqlLader.getKlant(klantId);

        klantFromDatabase.setAdres(adresLader.getAdres(klantId));
        klantFromDatabase.setContactPersonen(persoonLader.getPersoon(klantId));

        return klantFromDatabase;
	}
}
