package nl.hu.bep.group4.bifi.lader.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.lader.PersoonLader;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public class PersoonLaderImplTest {
	@Test
	public void testGetPersonen() throws ClassNotFoundException, SQLException, GarbageDataException, IOException {
		final int testKlantId = 5;
		final List<Persoon> personen = new ArrayList<>();
		personen.add(new Persoon(3, "Jan", "van", "Jansen", "030-555555","fax",Persoon.Geslacht.MAN));
		PersoonLader persoonLader = setup(testKlantId, personen, null);
		
		assertEquals(personen, persoonLader.getPersonen(testKlantId));
	}
	
	@Test
	public void testGetPersoon() throws ClassNotFoundException, SQLException, GarbageDataException, IOException {
		final Persoon testPersoon = new Persoon(3, "Jan", "van", "Jansen", "030-555555","fax",Persoon.Geslacht.MAN);
		PersoonLader persoonLader = setup(0, null, testPersoon);
		
		assertEquals(testPersoon, persoonLader.getPersoon(testPersoon.getId()));
	}

	private PersoonLader setup(final int testKlantId, final List<Persoon> personen, final Persoon testPersoon) {
		PersoonLader persoonLader = new PersoonLaderImpl(new MysqlLader() {
			@Override
			public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException {
				return null;
			}

			@Override
			public Klant getKlant(int klantId) throws SQLException, ClassNotFoundException {
				return null;
			}

			@Override
			public List<Persoon> getPersonen(int klantId)
					throws SQLException, ClassNotFoundException, GarbageDataException {
				assertEquals(testKlantId, klantId);
				return personen;
			}

			@Override
			public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException {
				assertEquals(testPersoon.getId(), persoonId);
				return testPersoon;
			}

			@Override
			public Adres getFactuurAdres(int klantId)
					throws SQLException, ClassNotFoundException, GarbageDataException {
				return null;
			}
		});
		return persoonLader;
	}
}
