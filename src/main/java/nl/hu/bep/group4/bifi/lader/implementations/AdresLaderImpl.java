package nl.hu.bep.group4.bifi.lader.implementations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.model.Adres;

public class AdresLaderImpl  implements AdresLader {
    private LegacyJarLader legacyJarLader;
    private MysqlLader mysqlLader;

    private static final char ADRESSINDICATOR = '-';

    public AdresLaderImpl(LegacyJarLader legacyJarLader, MysqlLader mysqlLader) {
        this.legacyJarLader = legacyJarLader;
        this.mysqlLader = mysqlLader;
    }

	/**
	 * Returns a list with Adres-objects found in the database with the given klantId.
     *
     * <p>
     * If an Adres.getStraat() starting with a '-' is found in the SQL database, the script will then read the key from the
     * SQL-result and search for the Adres-object in the LegacyJar.
     *
	 * @param klantId primary key of Klant in database
	 * @return A list object with 0 ... n Adres-objects
	 * @throws SQLException when SQL goes shitstorn
	 * @throws IOException because
	 * @throws ClassNotFoundException because
	 */
	@Override
	public List<Adres> getAdressen(int klantId) throws SQLException, IOException, ClassNotFoundException {

		List<Adres> mysqlAdressen = mysqlLader.getAdressen(klantId);
		List<Adres> resultaat = new ArrayList<>();
		List<String> adresIdLegacy = new ArrayList<>();

		if (mysqlAdressen.isEmpty()) {
			return resultaat;
		}
		
		for (Adres adres : mysqlAdressen) {
			String straat = adres.getStraat();
			if (!("").equals(straat)) {
				char firstChar = straat.charAt(0);
				if (firstChar == ADRESSINDICATOR) {
					String sleutel = straat.substring(1);
					adresIdLegacy.add(sleutel);
				}
				else {
					resultaat.add(adres);
				}
			}
		}
		
		if (!adresIdLegacy.isEmpty()) {
			for (String legacyKey : adresIdLegacy) {
				Adres adres = legacyJarLader.laadAdres(legacyKey);
				resultaat.add(adres);
			}
		}
		return resultaat;
	}

    @Override
    public Adres getFactuurAdres(int klantId) throws GarbageDataException, SQLException, ClassNotFoundException, IOException {

	    Adres factuurAdres = mysqlLader.getFactuurAdres(klantId);

	    if(!("").equals(factuurAdres.getStraat()) && factuurAdres.getStraat().charAt(0) == ADRESSINDICATOR) {
	        String legacyId = factuurAdres.getStraat().substring(1);
            return legacyJarLader.laadAdres(legacyId);
        }

        return factuurAdres;
    }
}
