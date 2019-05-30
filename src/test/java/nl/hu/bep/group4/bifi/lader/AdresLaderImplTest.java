package nl.hu.bep.group4.bifi.lader;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.sql.SQLException;
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

				return null;
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
				// TODO Auto-generated method stub
				return null;
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
		assertNull(lader.getAdres(0));
	}
	
}
