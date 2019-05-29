package nl.hu.bep.group4.bifi.lader.implementations;

import java.util.List;

import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.model.Adres;

public class AdresLaderImpl  implements AdresLader {
    private LegacyJarLader legacyJarLader;
    private MysqlLader mysqlLader;

    private int AdresID;
    private String straat;
    private String huisnummer;
    private String postcode;
    private String plaats;
    private String BIC;
    private boolean type;

    public AdresLaderImpl(LegacyJarLader legacyJarLader, MysqlLader mysqlLader) {
        this.legacyJarLader = legacyJarLader;
        this.mysqlLader = mysqlLader;
    }

	@Override
	public List<Adres> getAdres(int klantId) {
		// TODO Auto-generated method stub
		return null;
	}
}
