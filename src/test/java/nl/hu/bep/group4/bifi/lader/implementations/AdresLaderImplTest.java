package nl.hu.bep.group4.bifi.lader.implementations;

import java.util.ArrayList;
import nl.hu.bep.group4.bifi.lader.AdresLader;
import nl.hu.bep.group4.bifi.lader.LegacyJarLader;
import nl.hu.bep.group4.bifi.lader.MysqlLader;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
			public Klant getKlant(int klantId) {
				return null;
			}

			@Override
			public Adres getFactuurAdres(int klantId) throws SQLException {
				return null;
			}

			@Override
			public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException {
				List<Adres> adressen = new ArrayList<>();
				switch(klantId) {
					case 0:
						return adressen;
					case 1:
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						adressen.add(new Adres("Steenweg","59","3511JN","Utrecht","DABAIE2D"));
						break;
					case 2:
						adressen.add(new Adres("Steenweg","32","3500EE","Utrecht","DABAIE2D"));
						adressen.add(new Adres("-MOATA",null,null,null,null));
						break;
					default:
						break;
				}
				return adressen;
			}

			@Override
			public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException {
				return null;
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
		assertEquals(a.getStraat(), "Steenweg");
		assertEquals(a.getHuisnummer(), "59");
		assertEquals(a.getPostcode(), "3511JN");
		assertEquals(a.getPlaats(), "Utrecht");
		assertEquals(a.getBiC(), "DABAIE2D");
	}
	
	@Test
	public void testAdresUitLegacyJarLader() throws SQLException, IOException, ClassNotFoundException {
		AdresLader lader = setup();
		Adres a = lader.getAdressen(2).get(1);
		assertEquals(a.getStraat(), "Ajax");
		assertEquals(a.getHuisnummer(), "5");
		assertEquals(a.getPostcode(), "1901CD");
		assertEquals(a.getPlaats(), "Rotterdam");
		assertEquals(a.getBiC(), "testBIC1");
	}
}
