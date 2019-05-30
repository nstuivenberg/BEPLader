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
		System.out.println(mongoLader.connectToMongoDB());
	}
	
	@Test
	public void getFacturenVoorMaandTest() throws GarbageDataException {
		mongo.getFacturenVoorMaand(4);
	}
	
	
}
