package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.lader.implementations.LegacyJarLaderImpl;
import nl.hu.bep.group4.bifi.model.Adres;

public class LegacyJarLaderTest {
	private LegacyJarLader lader;
	
	public void setup() {
		lader = new LegacyJarLaderImpl();
	}
	
	@Test
	public void testAdressSleutelMOATA() throws IOException {
	    setup();
		Adres adres = lader.laadAdres("MOATA");
		assertNotNull(adres);
		assertEquals("Rotterdam", adres.getPlaats());
		assertEquals("1901CD", adres.getPostcode());
		assertEquals("Ajax", adres.getStraat());
		assertEquals("5", adres.getHuisnummer());
		assertNull(null, adres.getBic());
	}

	@Test
	public void testAdressSleutelKDLRA() throws IOException  {
	    setup();
		Adres adres = lader.laadAdres("KDLRA");
		assertNotNull(adres);
		assertEquals("Zevenhuizen", adres.getPlaats());
		assertEquals("4302CD", adres.getPostcode());
		assertEquals("Wassenlaan", adres.getStraat());
		assertEquals("358", adres.getHuisnummer());
		assertEquals(null, adres.getBic());
	}
	
	@Test
	public void testMeerdereAdressenAchterElkaar() throws IOException { //voor het geval de legacy jar daar niet tegen kan
	    setup();
		testAdressSleutelMOATA();
		testAdressSleutelKDLRA();
	}
	
	@Test
	public void testNietBestaandeAdressSleutel() throws IOException {
	    setup();
		Adres adres = lader.laadAdres("niet-bestaand");
		assertNull(adres);
	}
}
