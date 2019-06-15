package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.MongoLaderImpl;

import org.junit.jupiter.api.Test;

public class MongoLaderImplTest {


	public MongoLaderImpl setup() {
		return new MongoLaderImpl();
	}
	
	@Test
	public void testConnection() {
		MongoLaderImpl mongoLader = setup();
		mongoLader.connectToMongoDB();
	}
	
	@Test
	public void getFacturenVoorMaandTest() throws GarbageDataException {
		MongoLaderImpl mongoLader = setup();
		mongoLader.getFacturenVoorMaand(4);
	}

	@Test
	public void setFactuurSettingsTest() throws GarbageDataException {
		MongoLaderImpl mongoLader = setup();
	}
	
	
}
