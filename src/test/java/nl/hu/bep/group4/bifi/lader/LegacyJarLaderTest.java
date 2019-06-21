package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import ADDRLOOKUPER.AddressLookerUPAlreadyCloosed;
import ADDRLOOKUPER.AddressLookerUPAlreadyLookinUP;
import ADDRLOOKUPER.AddressLookerUPAlreadyReadyToLookUP;
import ADDRLOOKUPER.AddressLookerUpInWrongStateException;
import nl.hu.bep.group4.bifi.lader.implementations.LegacyJarLaderImpl;
import nl.hu.bep.group4.bifi.lader.implementations.LegacyJarWrapper;
import nl.hu.bep.group4.bifi.model.Adres;

public class LegacyJarLaderTest {
	private LegacyJarLader lader;
	
	public void setup() {
		lader = new LegacyJarLaderImpl(new LegacyJarWrapper());
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
	
	@Test
	public void testAddressLookerUPAlreadyLookinUPException() throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP, AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		testException(new AddressLookerUPAlreadyLookinUP());
	}
	
	@Test
	public void testAddressLookerUPAlreadyReadyToLookUPException() throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP, AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		testException(new AddressLookerUPAlreadyReadyToLookUP());
	}
	
	@Test
	public void testAddressLookerUpInWrongStateException() throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP, AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		testException(new AddressLookerUpInWrongStateException());
	}
	
	@Test
	public void testAddressLookerUPAlreadyCloosedException() throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP, AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		testException(new AddressLookerUPAlreadyCloosed());
	}

	private void testException(Exception exceptionToTest) throws AddressLookerUpInWrongStateException, AddressLookerUPAlreadyLookinUP,
			AddressLookerUPAlreadyReadyToLookUP, AddressLookerUPAlreadyCloosed {
		LegacyJarWrapper wrapper = EasyMock.createMock(LegacyJarWrapper.class);
		lader = new LegacyJarLaderImpl(wrapper);
		EasyMock.expect(wrapper.laadAdres("testkey")).andThrow(exceptionToTest);
		EasyMock.replay(wrapper);
		assertThrows(IOException.class, () -> {
			lader.laadAdres("testkey");
		});
		EasyMock.verify(wrapper);
	}
}
