package nl.hu.bep.group4.bifi.lader.implementations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public List<Adres> getAdres(int klantId) throws SQLException, IOException {
		ArrayList<String> adresSleutelsTeVindenInLegacy = new ArrayList<String>();
		List<Adres> resultaat = new ArrayList<>();
		// Kan een Klant een klantId van 0 hebben?
		// Of wordt -1 gebruikt als Id van ontbrekende Klant?
		if (klantId == 0) {
			return null;
		}
		List<Adres> mysqlAdressen = mysqlLader.getAdres(klantId);
		
		/*
		List<Adres> adres = null;
		try {
			adres = mysqlLader.getAdres(klantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (adres == null) {
			return null;
		}
		*/
		
		if (mysqlAdressen.isEmpty()) {
			return null;
		}
		
		for (Adres adres : mysqlAdressen) {
			String straat = adres.getStraat();
			char eersteChar;
			if (straat == null || straat.equals("")) {
				//
			}
			else {
				eersteChar = straat.charAt(0);
				if (eersteChar == '-') {
					String sleutel = straat.substring(1);
					adresSleutelsTeVindenInLegacy.add(sleutel);
				}
				else {
					resultaat.add(adres);
				}
			}
		}
		
		if (adresSleutelsTeVindenInLegacy.isEmpty()) {
			//
		}
		else {
			for (String sleutel : adresSleutelsTeVindenInLegacy) {
				Adres a = legacyJarLader.laadAdres(sleutel);
				resultaat.add(a);
			}
		}
		
		return resultaat;
	}
}
