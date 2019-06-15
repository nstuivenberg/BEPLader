package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.MysqlLaderImpl;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;
import nl.hu.bep.group4.bifi.model.Persoon.Geslacht;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MysqlLaderImplTest {
    MysqlLaderImpl sql = null;

    public void setup() {
         sql = new MysqlLaderImpl();
    }

    @Test
    public void testGetAdressen() throws ClassNotFoundException, SQLException, IOException {
    	setup();
        List<Adres> aL = sql.getAdressen(1);
        Adres a = aL.get(0);
        assertEquals("Steenweg", a.getStraat());
		assertEquals("59", a.getHuisnummer());
		assertEquals("3511JN", a.getPostcode());
		assertEquals("Utrecht", a.getPlaats());
		assertEquals("DABAIE2D", a.getBiC());
    }
    
    @Test
    public void testGetKlant() throws ClassNotFoundException, SQLException, IOException {
    	setup();
        Klant k = sql.getKlant(1);
        assertEquals(1, k.getId());
        assertEquals("Helma", k.getBedrijfsnaam());
        assertEquals("bv", k.getRechtsvorm());
        assertEquals("NL001234567B01", k.getVAT());
        assertEquals("NL91ABNA0417164300", k.getBankrekeningNummer());
        assertEquals(null, k.getGiroNummer());
        assertEquals("DABAIE2D", k.getBiC());
        //
    }
    
    @Test
    public void testGetPersonen() throws ClassNotFoundException, SQLException, IOException {
    	setup();
    	List<Persoon> lP = sql.getPersonen(1);
    	Persoon p = lP.get(0);
    	assertEquals(1, p.getId());
    	assertEquals("Jan", p.getVoornaam());
    	assertEquals(null, p.getTussenvoegsel());
    	assertEquals("Janssen", p.getAchternaam());
    	assertEquals("612345678", p.getTelefoon());
    	assertEquals("012-3456789", p.getFax());
    	assertEquals(Geslacht.MAN, p.getGeslacht());
    }

    @Test
    public void testGetPersonenMetGeslacht0() throws ClassNotFoundException, SQLException, IOException {
    	setup();
        List<Persoon> lP = sql.getPersonen(1);
        assertEquals(Persoon.Geslacht.MAN, lP.get(0).getGeslacht());
    }
    
    @Test
    public void testGetPersonenMetGeslachtM() throws ClassNotFoundException, SQLException, IOException {
    	setup();
        List<Persoon> lP = sql.getPersonen(2);
        assertEquals(Persoon.Geslacht.MAN, lP.get(0).getGeslacht());
    }

    @Test
    public void testGetPersonenMetGeslachtV() throws ClassNotFoundException, SQLException, IOException {
    	setup();
        List<Persoon> lP = sql.getPersonen(3);
        assertEquals(Persoon.Geslacht.VROUW, lP.get(0).getGeslacht());
    }
    
    @Test
    public void testGetFactuurAdres() throws ClassNotFoundException, SQLException, GarbageDataException, IOException {
    	setup();
    	Adres fA = sql.getFactuurAdres(1);
        assertEquals("Steenweg", fA.getStraat());
		assertEquals("59", fA.getHuisnummer());
		assertEquals("3511JN", fA.getPostcode());
		assertEquals("Utrecht", fA.getPlaats());
		assertEquals("DABAIE2D", fA.getBiC());
    }

    @Test
    public void testGetPersoon() throws ClassNotFoundException, SQLException, GarbageDataException, IOException {
    	setup();
        Persoon p = sql.getPersoon(1);
    	assertEquals("Jan", p.getVoornaam());
    	assertEquals(null, p.getTussenvoegsel());
    	assertEquals("Janssen", p.getAchternaam());
    	assertEquals("612345678", p.getTelefoon());
    	assertEquals("012-3456789", p.getFax());
    	assertEquals(Geslacht.MAN, p.getGeslacht());
    }

    @Test
    public void testGetPersoonMetGeslacht0() throws ClassNotFoundException, SQLException, IOException, GarbageDataException {
    	setup();
        Persoon p = sql.getPersoon(1);
        assertEquals(Persoon.Geslacht.MAN, p.getGeslacht());
    }
    
    @Test
    public void testGetPersoonMetGeslachtM() throws ClassNotFoundException, SQLException, IOException, GarbageDataException {
    	setup();
    	Persoon p = sql.getPersoon(2);
        assertEquals(Persoon.Geslacht.MAN, p.getGeslacht());
    }

    @Test
    public void testGetPersoonMetGeslachtV() throws ClassNotFoundException, SQLException, IOException, GarbageDataException {
    	setup();
    	Persoon p = sql.getPersoon(3);
        assertEquals(Persoon.Geslacht.VROUW, p.getGeslacht());
    }
}
