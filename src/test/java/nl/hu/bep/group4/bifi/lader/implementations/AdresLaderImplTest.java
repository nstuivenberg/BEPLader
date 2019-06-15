package nl.hu.bep.group4.bifi.lader.implementations;

import java.util.ArrayList;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.lader.implementations.AdresLaderImpl;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public class AdresLaderImplTest {

	private AdresLader setup() {
		LegacyJarLader legacyJarLader = new LegacyJarLader() {
			@Override
			public Adres laadAdres(String sleutel) throws IOException {
				Adres adres;
				switch(sleutel) {
					case "MOATA":
						adres = new Adres("Ajax", "5", "1901CD", "Rotterdam", "testBIC1");
						break;
					default:
						return null;
				}
				return adres;
			}
		};
		MysqlLader mysqlLader = new MysqlLader() {
			@Override
			public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException {
				List<Adres> adressen = new ArrayList<>();
				switch(klantId) {
					case 1:
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						break;
					case 2:
						adressen.add(new Adres("Steenweg","32","3500EE","Utrecht","DABAIE2D"));
						adressen.add(new Adres("-MOATA",null,null,null,null));
						break;
					case 3:
						adressen.add(new Adres("","","","",""));
						break;
						/*
					case 4:
						adressen.add(new Adres(null,null,null,null,null));
						break;
					case 5:
						adressen.add(null);
						break;
						*/
					default:
						break;
				}
				return adressen;
			}
			
			@Override
			public Klant getKlant(int klantId) {
				return null;
			}

			@Override
			public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException {
				return null;
			}

			@Override
			public Adres getFactuurAdres(int klantId) throws SQLException {
				Adres adres = null;
				switch(klantId) {
					case 1:
						adres = new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D");
						break;
					case 2:
						adres = new Adres("-MOATA",null,null,null,null);
						break;
					case 3:
						adres = new Adres("","","","","");
						break;
						/*
					case 4:
						adres = new Adres(null,null,null,null,null);
						break;
						*/
					default:
						break;
				}
				return adres;
			}

			@Override
			public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException {
				return null;
			}
		};
		return new AdresLaderImpl(legacyJarLader, mysqlLader);
	}

	@Test
	public void testAdresUitMysqlLader() throws SQLException, IOException, ClassNotFoundException {
		AdresLader lader = setup();
		Adres a = lader.getAdressen(1).get(0);
		assertEquals("Steenweg", a.getStraat());
		assertEquals("59", a.getHuisnummer());
		assertEquals("3511JN", a.getPostcode());
		assertEquals("Utrecht", a.getPlaats());
		assertEquals("DABAIE2D", a.getBic());
	}
	
	@Test
	public void testAdresUitLegacyJarLader() throws SQLException, IOException, ClassNotFoundException {
		AdresLader lader = setup();
		Adres a = lader.getAdressen(2).get(1);
		assertEquals("Ajax", a.getStraat());
		assertEquals("5", a.getHuisnummer());
		assertEquals("1901CD", a.getPostcode());
		assertEquals("Rotterdam", a.getPlaats());
		assertEquals("testBIC1", a.getBic());
	}
	
	@Test
	public void testAdresZonderStraat() throws ClassNotFoundException, SQLException, IOException {
		AdresLader lader = setup();
		List<Adres> aL = lader.getAdressen(3);
		assertTrue(aL.isEmpty());
	}
	
	@Test
	public void testAdresVanOngeldigeKlant() throws SQLException, IOException, ClassNotFoundException {
		AdresLader lader = setup();
		List<Adres> aL = lader.getAdressen(6);
		assertTrue(aL.isEmpty());
	}
	
	@Test
	public void testFactuurAdresUitMysqlLader() throws ClassNotFoundException, GarbageDataException, SQLException, IOException {
		AdresLader lader = setup();
		Adres a = lader.getFactuurAdres(1);
		assertEquals("Steenweg", a.getStraat());
		assertEquals("59", a.getHuisnummer());
		assertEquals("3511JN", a.getPostcode());
		assertEquals("Utrecht", a.getPlaats());
		assertEquals("DABAIE2D", a.getBic());
	}
	
	@Test
	public void testFactuurAdresUitLegacyJarLader() throws ClassNotFoundException, GarbageDataException, SQLException, IOException {
		AdresLader lader = setup();
		Adres a = lader.getFactuurAdres(2);
		assertEquals("Ajax", a.getStraat());
		assertEquals("5", a.getHuisnummer());
		assertEquals("1901CD", a.getPostcode());
		assertEquals("Rotterdam", a.getPlaats());
		assertEquals("testBIC1", a.getBic());
	}
	
	@Test
	public void testFactuurAdresZonderStraat() throws ClassNotFoundException, GarbageDataException, SQLException, IOException {
		AdresLader lader = setup();
		Adres a = lader.getFactuurAdres(3);
		assertEquals("", a.getStraat());
		assertEquals("", a.getHuisnummer());
		assertEquals("", a.getPostcode());
		assertEquals("", a.getPlaats());
		assertEquals("", a.getBic());
	}
}
