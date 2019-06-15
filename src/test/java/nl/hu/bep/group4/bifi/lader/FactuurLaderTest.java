package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.FactuurLaderImpl;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public class FactuurLaderTest {
	
	@Test
	public void testMaand1() throws ClassNotFoundException, GarbageDataException, SQLException, IOException {
		testVoorMaand(1);
	}
	
	@Test
	public void testMaand2() throws ClassNotFoundException, GarbageDataException, SQLException, IOException {
		testVoorMaand(2);
	}
	
	private void testVoorMaand(final int testMaand) throws GarbageDataException, ClassNotFoundException, SQLException, IOException {
		final Klant testKlant = new Klant(42, "TestBedrijf", "test-rechtsvorm", "asds", "sadfgedfd", "fdghgdfjk", null, null, null, null);
		final Klant testKlant2 = new Klant(43, "TestBedrijf2", "test-rechtsvorm2", "asdsdfsds", "safggdfgedfd", "fddfgghgdfjk", null, null, null, null);
		var klantLader = new KlantLader() {
			public boolean aangeroepen = false;
			@Override
			public Klant getKlant(int klantId) throws SQLException, IOException, ClassNotFoundException {
				aangeroepen = true;
				switch(klantId) {
					case(42):
						return testKlant;
					case(43):
						return testKlant2;
					default:
						throw new IOException();
				}
			}
		};
		final Persoon testContactPersoon1 = new Persoon(5, "Jan", "", "Smit", "030-555555", "", Persoon.Geslacht.MAN);
		var persoonLader = new PersoonLader() {
			public boolean aangeroepen = false;
			@Override
			public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException {
				return null;
			}
			@Override
			public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException {
				aangeroepen = true;
				switch(persoonId) {
					case(5):
						return testContactPersoon1;
					case(20):
						return testContactPersoon1;
					default:
						throw new SQLException();
				}
			}
			
		};
		final List<Factuur> testFacturen = new ArrayList<Factuur>();
		testFacturen.add(new Factuur(new Klant(42), null, 1, null, "de opmerking", new Persoon(5)));
		testFacturen.add(new Factuur(new Klant(43), null, 1, null, "nog een opmerking", new Persoon(20)));
		var mongoLader = new MongoLader() {
			public boolean aangeroepen = false;
			@Override
			public List<Factuur> getFacturenVoorMaand(int maandNummer) throws GarbageDataException {
				aangeroepen = true;
				assertEquals(testMaand, maandNummer);
				return testFacturen;
			}
		};
		FactuurLaderImpl factuurLader = new FactuurLaderImpl(klantLader, persoonLader, mongoLader);
		List<Factuur> facturen = factuurLader.getFacturenVoorMaand(testMaand);
		assertNotNull(facturen);
		assertEquals(testFacturen.size(), facturen.size());
		assertEquals(testKlant, testFacturen.get(0).getKlant());
		assertEquals(testContactPersoon1, testFacturen.get(0).getContactPersoon());
		assertEquals(testKlant2, testFacturen.get(1).getKlant());
		assertTrue(mongoLader.aangeroepen);
		assertTrue(klantLader.aangeroepen);
		assertTrue(persoonLader.aangeroepen);
	}
}
