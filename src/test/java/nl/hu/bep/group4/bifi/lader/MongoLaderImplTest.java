package nl.hu.bep.group4.bifi.lader;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.implementations.MongoLaderImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MongoLaderImplTest {

	MongoLaderImpl mongo = null;
	
	@BeforeEach
	public void BeforeEach() {
		mongo = new MongoLaderImpl();
	}
	
	@Test
	public void testConnection() {
		mongo.connectToMongoDB();
		System.out.println(mongo.connectToMongoDB());
	}
	
	@Test
	public void getFacturenVoorMaandTest() throws GarbageDataException {
		mongo.getFacturenVoorMaand(4);
	}
	
	
}
