package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.lader.implementations.AdresLaderImpl;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public class AdresLaderImplTest {
	private AdresLader lader;
	
	@BeforeEach
	public void setup() {
		LegacyJarLader legacyJarLader = new LegacyJarLader() {
			@Override
			public Adres laadAdres(String sleutel) throws IOException {
				Adres adres;
				switch(sleutel) {
					case "MOATA":
						adres = new Adres("Ajax", "5", "1901CD", "Rotterdam", "testBIC1");
						break;
					/*
					case "NIPJK":
						adres = new Adres("Hakkelaar", "90", "5202HL", "Den Haag", "testBIC2");
						break;
					case "KDLRA":
						adres = new Adres("Wassenlaan", "358", "4302CD", "Zevenhuizen", "testBIC3");
						break;
					*/
					default:
						return null;
				}
				return adres;
			}
		};
		MysqlLader mysqlLader = new MysqlLader() {			
			@Override
			public Klant getKlant(int klantId) {
				//
				return null;
			}

			@Override
			public Adres getFactuurAdres(int klantId) throws SQLException {
				return null;
			}

			@Override
			public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException {
				List<Adres> adressen = new ArrayList<Adres>();
				switch(klantId) {
					case 0:
						return null;
					case 1:
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						break;
					case 2:
						adressen.add(new Adres("Steenweg","32","3500EE","Utrecht","DABAIE2D"));
						adressen.add(new Adres("-MOATA",null,null,null,null));
						break;
					/*
					case 3:
						adressen.add(new Adres("-NIPJK",null,null,null,null));
						adressen.add(new Adres("-KDLRA",null,null,null,null));
						break;
					*/
					default:
						break;
				}
				return adressen;
				
			}

			@Override
			public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		lader = new AdresLaderImpl(legacyJarLader, mysqlLader);
	}
	
	@Test
	public void testOntbrekendeKlant() throws SQLException, IOException, ClassNotFoundException {
		List<Adres> al = lader.getAdressen(0);
		assertNull(al);
	}
	
	@Test
	public void testAdresUitMysqlLader() throws SQLException, IOException, ClassNotFoundException {
		Adres a = lader.getAdressen(1).get(0);
		assertEquals(a.getStraat(), "Steenweg");
		assertEquals(a.getHuisnummer(), "59");
		assertEquals(a.getPostcode(), "3511JN");
		assertEquals(a.getPlaats(), "Utrecht");
		assertEquals(a.getBiC(), "DABAIE2D");
	}
	
	@Test
	public void testAdresUitLegacyJarLader() throws SQLException, IOException, ClassNotFoundException {
		Adres a = lader.getAdressen(2).get(1);
		assertEquals(a.getStraat(), "Ajax");
		assertEquals(a.getHuisnummer(), "5");
		assertEquals(a.getPostcode(), "1901CD");
		assertEquals(a.getPlaats(), "Rotterdam");
		assertEquals(a.getBiC(), "testBIC1");
	}
	
	@Test
	public void testAdresVanOngeldigeKlant() throws SQLException, IOException, ClassNotFoundException {
		List<Adres> al = lader.getAdressen(4);
		assertNull(al);
	}
}
