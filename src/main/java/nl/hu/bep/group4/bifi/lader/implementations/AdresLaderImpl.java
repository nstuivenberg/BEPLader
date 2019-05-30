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

    public AdresLaderImpl(LegacyJarLader legacyJarLader, MysqlLader mysqlLader) {
        this.legacyJarLader = legacyJarLader;
        this.mysqlLader = mysqlLader;
    }

	@Override
	public List<Adres> getAdressen(int klantId) throws SQLException, IOException, ClassNotFoundException {
		ArrayList<String> adresSleutelsTeVindenInLegacy = new ArrayList<>();
		List<Adres> resultaat = new ArrayList<>();
		// Kan een Klant een klantId van 0 hebben?
		// Of wordt -1 gebruikt als Id van ontbrekende Klant?
		if (klantId == 0) {
			return null;
		}
		List<Adres> mysqlAdressen = mysqlLader.getAdressen(klantId);
		
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
			if (straat == null || straat.equals("")) {
				//
			}
			else {
				char eersteChar = straat.charAt(0);
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

    @Override
    public Adres getFactuurAdres(int klantId) {
        return null;
    }
}
